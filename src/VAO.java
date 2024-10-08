import static org.lwjgl.opengl.GL30.*;

public class VAO {
    private int id;
    private int numIndices;

    public void criarVAO(float[] vertices, float[] normais, int[] indices) {
        id = glGenVertexArrays();
        glBindVertexArray(id);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        int normalVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalVbo);
        glBufferData(GL_ARRAY_BUFFER, normais, GL_STATIC_DRAW);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        numIndices = indices.length;

        glBindVertexArray(0);
    }

    public void renderizar() {
        glBindVertexArray(id);  // Ativar VAO
        glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, 0);  // Renderizar com índices
        glBindVertexArray(0);
    }
}
