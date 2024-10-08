import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import java.util.Objects;

public class ModelLoader {
    private VAO vao;
    private shaders.Shader shader;

    public void carregarModelo(String caminhoArquivo) {
        AIScene cena = Assimp.aiImportFile(caminhoArquivo, Assimp.aiProcess_Triangulate | Assimp.aiProcess_GenSmoothNormals | Assimp.aiProcess_FlipUVs);

        if (cena == null) {
            throw new RuntimeException("Erro ao carregar modelo 3D: " + Assimp.aiGetErrorString());
        }

        processarNo(Objects.requireNonNull(cena.mRootNode()), cena);

        shader = new shaders.Shader("C:/Users/Luanm/Documents/demo/TBGA-Java/src/shaders/vertex_shader.glsl", "C:/Users/Luanm/Documents/demo/TBGA-Java/src/shaders/fragment_shader.glsl");
    }

    private void processarNo(AINode no, AIScene cena) {
        for (int i = 0; i < no.mNumMeshes(); i++) {
            AIMesh mesh = AIMesh.create(Objects.requireNonNull(cena.mMeshes()).get(Objects.requireNonNull(no.mMeshes()).get(i)));
            processarMalha(mesh);
        }

        for (int i = 0; i < no.mNumChildren(); i++) {
            processarNo(AINode.create(Objects.requireNonNull(no.mChildren()).get(i)), cena);
        }
    }

    private void processarMalha(AIMesh mesh) {
        float[] vertices = new float[mesh.mNumVertices() * 3];
        float[] normais = new float[mesh.mNumVertices() * 3];
        int[] indices = new int[mesh.mNumFaces() * 3];

        for (int i = 0; i < mesh.mNumVertices(); i++) {
            vertices[i * 3] = mesh.mVertices().get(i).x();
            vertices[i * 3 + 1] = mesh.mVertices().get(i).y();
            vertices[i * 3 + 2] = mesh.mVertices().get(i).z();

            normais[i * 3] = mesh.mNormals().get(i).x();
            normais[i * 3 + 1] = mesh.mNormals().get(i).y();
            normais[i * 3 + 2] = mesh.mNormals().get(i).z();
        }

        for (int i = 0; i < mesh.mNumFaces(); i++) {
            indices[i * 3] = mesh.mFaces().get(i).mIndices().get(0);
            indices[i * 3 + 1] = mesh.mFaces().get(i).mIndices().get(1);
            indices[i * 3 + 2] = mesh.mFaces().get(i).mIndices().get(2);
        }

        vao = new VAO();
        vao.criarVAO(vertices, normais, indices);
    }

    public void renderizar(Camera camera) {
        shader.usar();

        Matrix4f model = new Matrix4f();
        model.setIdentity();

        float cameraYaw = camera.getYaw();
        float cameraPitch = camera.getPitch();

        Vector3f front = new Vector3f();
        front.x = (float) Math.cos(Math.toRadians(cameraYaw)) * (float) Math.cos(Math.toRadians(cameraPitch));
        front.y = (float) Math.sin(Math.toRadians(cameraPitch));
        front.z = (float) Math.sin(Math.toRadians(cameraYaw)) * (float) Math.cos(Math.toRadians(cameraPitch));
        front.normalize();

        Vector3f eye = new Vector3f(camera.getPosX(), camera.getPosY(), camera.getPosZ());
        Vector3f center = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

        center.add(eye, front);

        Matrix4f view = new Matrix4f();
        lookAt(view, eye, center, up);

        Matrix4f projection = new Matrix4f();
        perspective(projection, (float) Math.toRadians(45.0f), 800f / 600f, 0.1f, 100.0f);

        shader.definirUniformeMatrix4f("model", model);
        shader.definirUniformeMatrix4f("view", view);
        shader.definirUniformeMatrix4f("projection", projection);

        vao.renderizar();
    }

    public void lookAt(Matrix4f matrix, Vector3f eye, Vector3f center, Vector3f up) {
        Vector3f f = new Vector3f();
        center.sub(eye, f);
        f.normalize();

        Vector3f u = new Vector3f(up);
        u.normalize();

        Vector3f s = new Vector3f();
        f.cross(u, s);
        s.normalize();

        u.cross(s, f);

        matrix.setIdentity();
        matrix.m00 = s.x;
        matrix.m01 = s.y;
        matrix.m02 = s.z;
        matrix.m10 = u.x;
        matrix.m11 = u.y;
        matrix.m12 = u.z;
        matrix.m20 = -f.x;
        matrix.m21 = -f.y;
        matrix.m22 = -f.z;

        matrix.m30 = -s.dot(eye);
        matrix.m31 = -u.dot(eye);
        matrix.m32 = f.dot(eye);
    }

    public void perspective(Matrix4f matrix, float fov, float aspect, float zNear, float zFar) {
        float tanFOV = (float) Math.tan(fov / 2.0f);
        float range = zNear - zFar;

        matrix.setZero();
        matrix.m00 = 1.0f / (tanFOV * aspect);
        matrix.m11 = 1.0f / tanFOV;
        matrix.m22 = -(zNear + zFar) / range;
        matrix.m23 = -1.0f;
        matrix.m32 = (2.0f * zFar * zNear) / range;
    }
}
