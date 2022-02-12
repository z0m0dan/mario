package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;

    public float r, g, b, a;
    private static Window window = null;
    private long glfwWindow;

    private static Scene currentScene = null;

    private static Scene scenes[] = {new LevelEditorScene(),new LevelScene()};

    private Window() {
        this.width = 1920;
        this.height = 1090;
        this.title = "Mario";
        r = 1.0f;
        g = 0;
        b = 0;
        a = 0;
    }

    public static void changeScene(int newScene) {
        if (newScene < 0 || newScene >= scenes.length)
            throw new IllegalStateException("La escena no existe");

        currentScene = scenes[newScene];
        //currentScene.init();
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {


        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //terminate glfw and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();


    }

    public void init() {
        //setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //initialize GLFW

        if (!glfwInit()) {
            throw new IllegalStateException("No se ha podido iniciar GLFW");
        }

        //configurar glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        //crear la ventana

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Error al crear la ventana de GLFW");
        }

        //registrar los eventos de mouse
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        //registrar los eventos de teclkado
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //Crear el contexto de OpenGL

        glfwMakeContextCurrent(glfwWindow);

        //Prender el Vsync

        glfwSwapInterval(1);

        //mostrar la ventana
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Obtener eventos
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt > 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        }
    }
}
