package jade;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {
    private boolean isChangingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene(){
        System.out.println("Creamos la escena del level editor, wuuu!");
    }

    @Override
    public void update(float dt) {

        System.out.println("Estamos a "+ (1.0f/dt) + "FPS");

        if (KeyListener.isKeyPressed(KeyEvent.VK_SPACE) && !isChangingScene){
            System.out.println("vamosa  cambiar la scene");
            isChangingScene = true;
        }

        if(isChangingScene && timeToChangeScene > 0){
            timeToChangeScene -= dt;
            Window.get().r -= dt * 1;
            Window.get().g -= dt * 1;
            Window.get().b -= dt * 1;

        }else if(isChangingScene){
            Window.changeScene(1);
        }
    }


}
