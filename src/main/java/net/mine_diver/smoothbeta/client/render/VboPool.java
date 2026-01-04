package net.mine_diver.smoothbeta.client.render;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.smoothbeta.client.render.gl.GlStateManager;
import net.mine_diver.smoothbeta.mixin.client.MinecraftAccessor;
import net.minecraft.client.util.GlAllocationUtils;
import net.mine_diver.smoothbeta.util.collection.LinkedList;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@SuppressWarnings("removal")
public class VboPool implements AutoCloseable {
    @SuppressWarnings("deprecation")
    private static final MinecraftAccessor mc = ((MinecraftAccessor) FabricLoader.getInstance().getGameInstance());

    private int vertexBufferId = GL15.glGenBuffers();
    private int capacity = 4096;
    private int nextPos = 0;
    private int size;
    private final LinkedList<Pos> posList = new LinkedList<>();
    private Pos compactPosLast = null;

    private IntBuffer firsts = GlAllocationUtils.allocateIntBuffer(this.capacity);
    private IntBuffer counts = GlAllocationUtils.allocateIntBuffer(this.capacity);
    private final int vertexBytes;
    private VertexFormat.DrawMode drawMode = VertexFormat.DrawMode.QUADS;

    public VboPool(VertexFormat format) {
        vertexBytes = format.getVertexSizeByte();
        this.bindBuffer();
        long i = this.toBytes(this.capacity);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, i, GL15.GL_STATIC_DRAW);
        this.unbindBuffer();
    }

    @Override
    public void close() {
        if (this.vertexBufferId > 0) {
            GlStateManager._glDeleteBuffers(this.vertexBufferId);
            this.vertexBufferId = 0;
        }
    }

    public void bufferData(ByteBuffer data, Pos poolPos) {
        if (this.vertexBufferId >= 0) {
            int position = poolPos.getPosition();
            int size = poolPos.getSize();
            int bufferSize = this.toVertex(data.limit());

            if (bufferSize <= 0) {
                if (position >= 0) {
                    poolPos.setPosition(-1);
                    poolPos.setSize(0);
                    this.posList.remove(poolPos.getNode());
                    this.size -= size;
                }
            } else {
                if (bufferSize > size) {
                    poolPos.setPosition(this.nextPos);
                    poolPos.setSize(bufferSize);
                    this.nextPos += bufferSize;

                    if (position >= 0)
                        this.posList.remove(poolPos.getNode());

                    this.posList.addLast(poolPos.getNode());
                }

                poolPos.setSize(bufferSize);
                this.size += bufferSize - size;
                this.checkVboSize(poolPos.getPositionNext());
                long l = this.toBytes(poolPos.getPosition());
                this.bindBuffer();
                GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, l, data);
                this.unbindBuffer();

                if (this.nextPos > this.size * 11 / 10)
                    this.compactRanges();
            }
        }
    }

    private void compactRanges() {
        if (!this.posList.isEmpty()) {
            Pos vborange = this.compactPosLast;

            if (vborange == null || !this.posList.contains(vborange.getNode()))
                vborange = this.posList.getFirst().getItem();

            int i;
            Pos vborange1 = vborange.getPrev();

            if (vborange1 == null)
                i = 0;
            else
                i = vborange1.getPositionNext();

            int j = 0;

            while (vborange != null && j < 1) {
                ++j;

                if (vborange.getPosition() == i) {
                    i += vborange.getSize();
                    vborange = vborange.getNext();
                } else {
                    int k = vborange.getPosition() - i;

                    if (vborange.getSize() <= k) {
                        this.copyVboData(vborange.getPosition(), i, vborange.getSize());
                        vborange.setPosition(i);
                        i += vborange.getSize();
                        vborange = vborange.getNext();
                    } else {
                        this.checkVboSize(this.nextPos + vborange.getSize());
                        this.copyVboData(vborange.getPosition(), this.nextPos, vborange.getSize());
                        vborange.setPosition(this.nextPos);
                        this.nextPos += vborange.getSize();
                        Pos vborange2 = vborange.getNext();
                        this.posList.remove(vborange.getNode());
                        this.posList.addLast(vborange.getNode());
                        vborange = vborange2;
                    }
                }
            }

            if (vborange == null)
                this.nextPos = this.posList.getLast().getItem().getPositionNext();

            this.compactPosLast = vborange;
        }
    }

    private long toBytes(int vertex) {
        return (long) vertex * (long) this.vertexBytes;
    }

    private int toVertex(long bytes) {
        return (int) (bytes / (long) this.vertexBytes);
    }

    private void checkVboSize(int sizeMin) {
        if (this.capacity < sizeMin)
            this.expandVbo(sizeMin);
    }

    private void copyVboData(int posFrom, int posTo, int size) {
        long i = this.toBytes(posFrom);
        long j = this.toBytes(posTo);
        long k = this.toBytes(size);
        ByteBuffer temp = GlAllocationUtils.allocateByteBuffer((int) k);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBufferId);
        GL15.glGetBufferSubData(GL15.GL_ARRAY_BUFFER, i, temp);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, j, temp);
        mc.smoothbeta_printOpenGLError("Copy VBO range");
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void expandVbo(int sizeMin) {
        int i;

        i = this.capacity * 6 / 4;
        while (i < sizeMin)
            i = i * 6 / 4;

        long j = this.toBytes(this.capacity);
        long k = this.toBytes(i);
        int l = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, l);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, k, GL15.GL_STATIC_DRAW);
        mc.smoothbeta_printOpenGLError("Expand VBO");

        ByteBuffer temp = GlAllocationUtils.allocateByteBuffer((int) j);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBufferId);
        GL15.glGetBufferSubData(GL15.GL_ARRAY_BUFFER, 0L, temp);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, l);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0L, temp);
        mc.smoothbeta_printOpenGLError("Copy VBO: " + k);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glDeleteBuffers(this.vertexBufferId);
        this.firsts = GlAllocationUtils.allocateIntBuffer(i);
        this.counts = GlAllocationUtils.allocateIntBuffer(i);
        this.vertexBufferId = l;
        this.capacity = i;
    }

    public void bindBuffer() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBufferId);
    }

    public void upload(VertexFormat.DrawMode drawMode, Pos range) {
        if (this.drawMode != drawMode) {
            if (this.firsts.position() > 0)
                throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + drawMode);

            this.drawMode = drawMode;
        }

        this.counts.put(range.getSize());
        this.firsts.put(range.getPosition());
    }

    public void drawAll() {
        if (this.firsts.position() == 0)
            return;

        this.bindBuffer();

        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 28, 0L);
        GL20.glEnableVertexAttribArray(1);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 28, 12L);
        GL20.glEnableVertexAttribArray(2);
        GL20.glVertexAttribPointer(2, 4, GL11.GL_UNSIGNED_BYTE, true, 28, 20L);
        GL20.glEnableVertexAttribArray(3);
        GL20.glVertexAttribPointer(3, 3, GL11.GL_BYTE, true, 28, 24L);

        this.firsts.flip();
        this.counts.flip();
        GL14.glMultiDrawArrays(this.drawMode.glMode, this.firsts, this.counts);
        this.firsts.clear();
        this.counts.clear();

        if (this.nextPos > this.size * 11 / 10)
            this.compactRanges();
    }

    public void unbindBuffer() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void deleteGlBuffers() {
        if (this.vertexBufferId >= 0) {
            GlStateManager._glDeleteBuffers(this.vertexBufferId);
            this.vertexBufferId = -1;
        }
    }

    public static class Pos {
        private int position = -1;
        private int size = 0;
        private final LinkedList.Node<Pos> node = new LinkedList.Node<>(this);

        public int getPosition() {
            return this.position;
        }

        public int getSize() {
            return this.size;
        }

        public int getPositionNext() {
            return this.position + this.size;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public LinkedList.Node<Pos> getNode() {
            return this.node;
        }

        public Pos getPrev() {
            LinkedList.Node<Pos> node = this.node.getPrev();
            return node == null ? null : node.getItem();
        }

        public Pos getNext() {
            LinkedList.Node<Pos> node = this.node.getNext();
            return node == null ? null : node.getItem();
        }

        public String toString() {
            return this.position + "/" + this.size + "/" + (this.position + this.size);
        }
    }
}
