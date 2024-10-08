import org.lwjgl.glfw.GLFW;

public class Camera {
    private float posX, posY, posZ;
    private float pitch, yaw;

    public Camera(float x, float y, float z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z; // Teste com z = 0.5f ou mais perto
        this.pitch = 0.0f;
        this.yaw = -90.0f;
    }

    public void atualizar(long window) {
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
            posZ -= 0.05f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            posZ += 0.05f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            posX -= 0.05f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            posX += 0.05f;
        }

        // Implementar controle de pitch e yaw com o mouse para uma câmera de primeira pessoa
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
