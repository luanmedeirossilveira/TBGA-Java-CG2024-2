import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private Camera camera;
    private ModelLoader modelLoader;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        init();
        loop();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Não foi possível inicializar o GLFW.");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Visualizador 3D", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Falha ao criar a janela GLFW.");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);

        GL43.glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
            System.err.println("GL Error: " + GLDebugMessageCallback.getMessage(length, message));
        }, 0);


        camera = new Camera(0, 0, 5.0f);

        modelLoader = new ModelLoader();
        modelLoader.carregarModelo("C:/Users/Luanm/Documents/demo/TBGA-Java/src/objects/Suzanne.obj");
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.atualizar(window);

            modelLoader.renderizar(camera);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
