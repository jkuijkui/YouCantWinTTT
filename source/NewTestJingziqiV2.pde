import ddf.minim.*;
import ddf.minim.effects.*;

Minim minim;
//Minim minim1;
AudioPlayer backMusic;
AudioPlayer clip;


GameManager GM;

void setup()
{
  size(300, 300);
  
  minim = new Minim(this);
  //minim1 = new Minim(this);
  backMusic = minim.loadFile("Moments.mp3", 2048); 
  clip = minim.loadFile("Pop.wav", 2048);
  backMusic.loop();
  
  GM = new GameManager();
  GM.Init();
}

void draw()
{  
  GM.GameUpdate();
}

//add listener here
void mouseReleased()
{
  //clip.play();
  
  if (GM.gameState == State.gameShowBeginUI)
  {
    GM.ui.humanFirst.MouseListener();
    GM.ui.compuFirst.MouseListener();
  }

  if (GM.gameState == State.gameHumanFirstOn)
  {
    boolean setSucessed = GM.resource.SetPaneRectGouOrChaForMouse(mouseX, mouseY, true);

    if (setSucessed)
    {
      GM.turnCount++;
    }

    println(GM.turnCount);

    redraw();
  }
  
  if (GM.gameState == State.gameCompuFirstOn)
  {
    if(GM.turnCount != 0)
    {
      boolean setSucessed = GM.resource.SetPaneRectGouOrChaForMouse(mouseX, mouseY, true);

      if (setSucessed)
      {
        GM.turnCount++;
      }

      println(GM.turnCount);

      redraw();      
    }
  }  

  if (GM.gameState == State.gameOverComputerWin || GM.gameState == State.gameOverDraw)
  {
    GM.ui.restart.MouseListener();
  }
  
  //backMusic.pause();
  
  //backMusic.loop();
}