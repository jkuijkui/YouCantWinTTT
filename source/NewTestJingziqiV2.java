import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.effects.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NewTestJingziqiV2 extends PApplet {




Minim minim;
//Minim minim1;
AudioPlayer backMusic;
AudioPlayer clip;


GameManager GM;

public void setup()
{
  
  
  minim = new Minim(this);
  //minim1 = new Minim(this);
  backMusic = minim.loadFile("Moments.mp3", 2048); 
  clip = minim.loadFile("Pop.wav", 2048);
  backMusic.loop();
  
  GM = new GameManager();
  GM.Init();
}

public void draw()
{  
  GM.GameUpdate();
}

//add listener here
public void mouseReleased()
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
class CompuFitstMidHumanNearMid extends Logic
{
  GameManager GMR;

  CompuFirstOnLogic father;

  Pane.PaneRect firstGou;  

  Pane.PaneRect secondGou;  

  Pane.PaneRect secondCha;  

  Pane.PaneRect thirdCha;

  Pane.PaneRect forthCha;

  boolean stepFourLock1; 

  boolean stepFourLock2; 

  CompuFitstMidHumanNearMid(GameManager _GMR, CompuFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;
  }    

  public void Update()
  {
    if (GMR.turnCount == 2)
    {
      firstGou = GMR.resource.r_pane.lastSelectedRect;

      GMR.resource.r_pane.SetSpecialRandomCorner(firstGou,false);

      secondCha = GMR.resource.r_pane.lastCompSelectedRect; 

      GMR.turnCount++;
    }  

    if (GMR.turnCount == 4)
    {
      if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
      {
        GMR.turnCount++;

        GMR.compuWin = true;
      } else
      {
        secondGou = GMR.resource.r_pane.lastSelectedRect;

        GMR.resource.r_pane.SetSpecialPositionForCorner1(firstGou, secondGou);

        thirdCha = GMR.resource.r_pane.lastCompSelectedRect;  

        GMR.turnCount++;
      }
    }

    if (GMR.turnCount == 6)
    {
      if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
      {
        GMR.turnCount++;

        GMR.compuWin = true;
      } else if (GMR.resource.r_pane.SetInsertRowOrColumn(thirdCha, secondCha, false))
      {
        GMR.turnCount++;

        GMR.compuWin = true;
      }
    }
  }
}

class CompuFitstMidHumanCorner extends Logic
{
  GameManager GMR;

  CompuFirstOnLogic father;

  Pane.PaneRect firstGou;  

  Pane.PaneRect secondGou;  

  Pane.PaneRect secondCha;  

  Pane.PaneRect thirdCha;

  Pane.PaneRect forthCha;

  boolean stepFourLock1; 

  boolean stepFourLock2;  

  CompuFitstMidHumanCorner(GameManager _GMR, CompuFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;
  }    

  public void Update()
  {
    if (GMR.turnCount == 2)
    {
      firstGou = GMR.resource.r_pane.lastSelectedRect;

      GMR.resource.r_pane.SetInversePosition(false);

      secondCha = GMR.resource.r_pane.lastCompSelectedRect; 

      GMR.turnCount++;
    }

    if (GMR.turnCount == 4)
    {
      secondGou = GMR.resource.r_pane.lastSelectedRect;

      if (GMR.resource.r_pane.CheckSameRowOrSameColumn(firstGou))
      {
        GMR.resource.r_pane.SetInsertRowOrColumn(secondGou, firstGou, false);

        thirdCha = GMR.resource.r_pane.lastCompSelectedRect;

        GMR.turnCount++;

        stepFourLock1 = true;
      } else 
      {
        GMR.resource.r_pane.SetSpecialPositionForCorner1(firstGou, secondGou);

        GMR.turnCount++;

        thirdCha = GMR.resource.r_pane.lastCompSelectedRect;

        stepFourLock2 = true;
      }
    }

    if (GMR.turnCount == 6)
    {
      if (stepFourLock1)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;

          GMR.compuWin = true;
        } else if (GMR.resource.r_pane.SetInsertRowOrColumn(thirdCha, secondCha, false))
        {
          GMR.turnCount++;

          GMR.compuWin = true;
        } else
        {
          GMR.resource.r_pane.SetRandomNearMid(false);

          forthCha = GMR.resource.r_pane.lastCompSelectedRect; 

          GMR.turnCount++;
        }
      } else if (stepFourLock2)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;

          GMR.compuWin = true;
        } else if (GMR.resource.r_pane.SetInsertRowOrColumn(thirdCha, secondCha, false))
        {
          GMR.turnCount++;

          GMR.compuWin = true;
        }
      }
    }

    if (GMR.turnCount == 8)
    {
      if (stepFourLock1)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(forthCha, false))
        {
          GMR.turnCount++;

          GMR.compuWin = true;
        } else
        {
          GMR.resource.r_pane.SetRandomPosition(false);

          GMR.turnCount++;
        }
      }
    }

    if (GMR.turnCount == 9)
    {
      if (!GMR.compuWin)
      {
        GMR.draw = true;
      }
    }
  }
}


class CompuFitstMid extends Logic
{
  GameManager GMR;

  CompuFirstOnLogic father;

  CompuFitstMid(GameManager _GMR, CompuFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;
  }  

  public void Update()
  {
    if (GMR.turnCount == 0)
    {
      GMR.resource.r_pane.SetMidPosition(false);

      GMR.turnCount++;
    }

    if (GMR.turnCount == 2)
    {
      if (GMR.resource.r_pane.CheckLastTurnInCorner())
      {
        //println("fafa");
        father.ChangeLogic(new CompuFitstMidHumanCorner(GMR, father));
      } else if (GMR.resource.r_pane.CheckLastTurnInNearMid())
      {
        //println("fdsada");
        father.ChangeLogic(new CompuFitstMidHumanNearMid(GMR, father));
      }
    }
  }
}

class CompuFirstOnLogic
{
  GameManager GMR;

  Logic logic;

  CompuFirstOnLogic(GameManager _GMR)
  {
    GMR = _GMR;

    logic = new CompuFitstMid(GMR, this);
  }

  public void ChangeLogic(Logic _logic)
  {
    logic = _logic;
    logic.Update();
  }

  public void Update()
  {
    logic.Update();
  }
}
enum State 
{
  gameShowBeginUI, gameHumanFirstOn, gameCompuFirstOn, gameOverComputerWin, gameOverDraw, gameRestart
};

class GameManager
{
  Resource resource;
  State gameState;
  UI ui;

  HumanFirstOnLogic humanFirstOnLogic;

  CompuFirstOnLogic compuFirstOnLogic;

  int turnCount;

  boolean compuWin;
  boolean draw;

  GameManager()
  {
  }

  public void Init()
  {
    LoadResource();

    InitGameEnvironment();
    InitGameState();
  }

  public void InitGameEnvironment()
  {
    frameRate(8);

    rectMode(CENTER);

    ui = new UI();

    turnCount = 0;
  }

  public void InitGameState()
  {
    gameState = State.gameShowBeginUI;

    humanFirstOnLogic = new HumanFirstOnLogic(this);

    compuFirstOnLogic = new CompuFirstOnLogic(this);

    compuWin = false;

    draw = false;
  }

  public void LoadResource()
  {
    resource = new Resource();
  }

  public void GameAutoMachineUpdate()
  {
    switch(gameState)
    {
    case gameShowBeginUI:
      ui.ShowBeginUI();
      if (ui.isHumanFirst)
      {
        gameState = State.gameHumanFirstOn;
        break;
      }
      if (ui.isCompuFirst)
      {
        gameState = State.gameCompuFirstOn;
        break;
      }
      break;
    case gameHumanFirstOn:
      ui.ShowGameOnUI();
      GameOnHumanFirstOn();
      break;
    case gameCompuFirstOn:
      ui.ShowGameOnUI();
      GameOnCompuFirstOn();
      break;
    case gameOverComputerWin:
      ui.ShowGameOverComputerWIn();
      if (ui.isRestart)
      {
        gameState = State.gameRestart;
        break;
      }
      break;
    case gameOverDraw:
      ui.ShowGameOverDraw();
      if (ui.isRestart)
      {
        gameState = State.gameRestart;
        break;
      }
      break;
    case gameRestart:
      Init();
      break;
    }
  }

  public void GameOnHumanFirstOn()
  {
    humanFirstOnLogic.Update();
    resource.Update();

    if (compuWin)
    {
      gameState = State.gameOverComputerWin;
    }

    if (draw)
    {
      gameState = State.gameOverDraw;
    }
  }


  public void GameOnCompuFirstOn()
  {
    compuFirstOnLogic.Update();
    resource.Update();

    if (compuWin)
    {
      gameState = State.gameOverComputerWin;
    }

    if (draw)
    {
      gameState = State.gameOverDraw;
    }
  }

  public void GameUpdate()
  {
    GameAutoMachineUpdate();
  }
}
abstract class Logic
{
  public abstract void Update();
}

class HumanNotMidCorner extends Logic
{
  GameManager GMR;

  HumanFirstOnLogic father;

  Pane.PaneRect firstGou;

  Pane.PaneRect secondGou;

  Pane.PaneRect thirdGou;

  Pane.PaneRect forthGou;

  Pane.PaneRect secondCha;

  Pane.PaneRect thirdCha;

  boolean stepThreeLock;

  boolean stepFiveLock1;

  boolean stepFiveLock2;  

  HumanNotMidCorner(GameManager _GMR, HumanFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;
  }

  public void Update()
  {
    if (GMR.turnCount == 1)
    {
      firstGou = GMR.resource.r_pane.lastSelectedRect;

      GMR.resource.r_pane.SetMidPosition(false);
      GMR.turnCount++;
    }


    if (GMR.turnCount == 3)
    {
      if (GMR.resource.r_pane.CheckSameRowOrSameColumn(firstGou))
      {
        GMR.resource.r_pane.SetInsertRowOrColumn(firstGou, false);

        secondCha = GMR.resource.r_pane.lastCompSelectedRect;

        GMR.turnCount++;

        stepThreeLock = true;
      } 
      else
      {
        if (!GMR.resource.r_pane.CheckIsDuiCornerGou(GMR.resource.r_pane.lastSelectedRect))
        {
          secondGou = GMR.resource.r_pane.lastSelectedRect;

          GMR.resource.r_pane.SetSpecialPosition(firstGou, secondGou);

          secondCha = GMR.resource.r_pane.lastCompSelectedRect;

          GMR.turnCount++;

          stepFiveLock1 = true;
        }
        else
        {
          secondGou = GMR.resource.r_pane.lastSelectedRect;

          GMR.resource.r_pane.SetRandomNearMid(false);

          secondCha = GMR.resource.r_pane.lastCompSelectedRect;

          GMR.turnCount++;

          stepFiveLock2 = true;
        }
      }
    }

    if (GMR.turnCount == 5)
    {
      if (stepThreeLock)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else 
        {
          thirdGou = GMR.resource.r_pane.lastSelectedRect;

          if (GMR.resource.r_pane.CheckSameRowOrSameColumn(firstGou, thirdGou))
          {
            GMR.resource.r_pane.SetInsertRowOrColumn(firstGou, false);
            thirdCha = GMR.resource.r_pane.lastCompSelectedRect;  
            GMR.turnCount++;
          } else
          {
            //SetRandomNearMid
            GMR.resource.r_pane.SetRandomNearMid(false);
            thirdCha = GMR.resource.r_pane.lastCompSelectedRect;         
            GMR.turnCount++;
          }
        }
      } else if (stepFiveLock1)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else
        {
          thirdGou = GMR.resource.r_pane.lastSelectedRect;

          GMR.resource.r_pane.SetInsertRowOrColumn(firstGou, false);

          GMR.turnCount++;
        }
      } else if (stepFiveLock2)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else
        {
          thirdGou = GMR.resource.r_pane.lastSelectedRect;

          if (GMR.resource.r_pane.CheckSameRowOrSameColumn(firstGou, thirdGou))
          {
            GMR.resource.r_pane.SetInsertRowOrColumn(firstGou, false);

            thirdCha = GMR.resource.r_pane.lastCompSelectedRect;

            GMR.turnCount++;
          } else if (GMR.resource.r_pane.CheckSameRowOrSameColumn(secondGou, thirdGou))
          {
            GMR.resource.r_pane.SetInsertRowOrColumn(secondGou, false);

            thirdCha = GMR.resource.r_pane.lastCompSelectedRect;

            GMR.turnCount++;
          }
        }
      }
    } 

    if (GMR.turnCount == 7)
    {
      if (stepThreeLock)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else
        {
          GMR.resource.r_pane.SetRandomPosition(false);
          GMR.turnCount++;
        }
      } else if (stepFiveLock1)
      {
        forthGou = GMR.resource.r_pane.lastSelectedRect;

        if (GMR.resource.r_pane.CheckSameRowOrSameColumn(thirdGou, forthGou))
        {
          GMR.resource.r_pane.SetInsertRowOrColumn(thirdGou, false);
          GMR.turnCount++;
        } else
        {
          GMR.resource.r_pane.SetRandomPosition(false);
          GMR.turnCount++;
        }
      } else if (stepFiveLock2)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else
        {
          //GMR.resource.r_pane.SetInsertRowOrColumn(firstGou,false);
          //GMR.turnCount++; 
          if (!GMR.resource.r_pane.SetMidRectGouOrCha(firstGou, GMR.resource.r_pane.lastSelectedRect))
          {
            GMR.resource.r_pane.SetMidRectGouOrCha(secondGou, GMR.resource.r_pane.lastSelectedRect);
          }

          GMR.turnCount++;
        }
      }
    }

    if (GMR.turnCount == 9)
    {
      if(!GMR.compuWin)
      {
        GMR.draw = true;
      }      
    }
  }
}

class HumanNotMidNearMid extends Logic
{
  GameManager GMR;

  HumanFirstOnLogic father;

  Pane.PaneRect firstGou;

  Pane.PaneRect secondGou;

  Pane.PaneRect thirdGou;

  Pane.PaneRect forthGou;

  Pane.PaneRect secondCha;

  Pane.PaneRect thirdCha;

  boolean stepThreeLock1;
  
  boolean stepThreeLock2;
  
  boolean stepThreeLock3;
  
  boolean stepThreeLock4;  

  boolean stepFiveLock1;

  boolean stepFiveLock2;  

  HumanNotMidNearMid(GameManager _GMR, HumanFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;
  }

  public void Update()
  {
    if (GMR.turnCount == 1)
    {
      firstGou = GMR.resource.r_pane.lastSelectedRect;

      GMR.resource.r_pane.SetMidPosition(false);

      GMR.turnCount++;
    }


    if (GMR.turnCount == 3)
    {
      secondGou = GMR.resource.r_pane.lastSelectedRect;
      
      if (GMR.resource.r_pane.CheckIsDuiCornerGou(firstGou))
      {
        GMR.resource.r_pane.SetRandomCorner(false);

        secondCha = GMR.resource.r_pane.lastCompSelectedRect;

        GMR.turnCount++;

        stepThreeLock1 = true;
      }
      else if(GMR.resource.r_pane.SetInsertRowOrColumn(firstGou,secondGou,false))
      {
        GMR.turnCount++;
        
        secondCha = GMR.resource.r_pane.lastCompSelectedRect;        
        
        stepThreeLock2 = true;
      }
      else if(GMR.resource.r_pane.CheckLastTurnInNearMid())
      {
        GMR.resource.r_pane.SetSpecialPositionForCorner(firstGou,secondGou);
        
        secondCha = GMR.resource.r_pane.lastCompSelectedRect;
        
        GMR.turnCount++;
        
        stepThreeLock3 = true;
      }
      else //is corner
      {
        GMR.resource.r_pane.SetSpecialPositionForCorner(firstGou,secondGou);
        
        secondCha = GMR.resource.r_pane.lastCompSelectedRect;
        
        GMR.turnCount++;        
        
        stepThreeLock4 = true;
      }
    }

    if (GMR.turnCount == 5)
    {
      thirdGou = GMR.resource.r_pane.lastSelectedRect;
      
      if (stepThreeLock1)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else
        {
          if (!GMR.resource.r_pane.SetInsertRowOrColumn(firstGou, thirdGou, false))
          {
            GMR.resource.r_pane.SetInsertRowOrColumn(secondGou, thirdGou, false);
          }

          thirdCha = GMR.resource.r_pane.lastCompSelectedRect;  

          GMR.turnCount++;
        }
      }
      else if(stepThreeLock2)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else
        {
          GMR.resource.r_pane.SetInsertRowOrColumn(secondGou,thirdGou,false);
          
          thirdCha = GMR.resource.r_pane.lastCompSelectedRect;
          
          GMR.turnCount++;
        }
      }
      else if(stepThreeLock3)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else
        {
          GMR.resource.r_pane.SetRandomCorner(false);
          
          thirdCha = GMR.resource.r_pane.lastCompSelectedRect; 
          
          GMR.turnCount++;
        }
      }
      else if(stepThreeLock4)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(secondCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else
        {
          GMR.resource.r_pane.SetInsertRowOrColumn(secondGou,thirdGou,false);
          
          thirdCha = GMR.resource.r_pane.lastCompSelectedRect;
          
          GMR.turnCount++;          
        }
      }
    } 

    if (GMR.turnCount == 7)
    {
      if (stepThreeLock1)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else if (GMR.resource.r_pane.SetMidRectGouOrCha(thirdCha, secondCha))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
      }
      else if(stepThreeLock2)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else
        {
          GMR.resource.r_pane.SetRandomPosition(false);
          GMR.turnCount++;          
        }
      }
      else if(stepThreeLock3)
      {
        if (GMR.resource.r_pane.CheckInverseAndSet(thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        }
        else
        {
          forthGou = GMR.resource.r_pane.lastSelectedRect;
          
          GMR.resource.r_pane.SetInsertRowOrColumn(thirdGou,forthGou,false);
          
          GMR.turnCount++;
        }
      }
      else if(stepThreeLock4)
      {
        if(GMR.resource.r_pane.CheckSameRowOrSameColumn(GMR.resource.r_pane.lastSelectedRect,thirdGou))
        {
          GMR.resource.r_pane.SetInsertRowOrColumn(thirdGou,GMR.resource.r_pane.lastSelectedRect,false);
          
          GMR.turnCount++;
        }
        else
        {
          GMR.resource.r_pane.SetRandomPosition(false);
          
          GMR.turnCount++;
        }
      }
    }

    if (GMR.turnCount == 9)
    {
      if(!GMR.compuWin)
      {
        GMR.draw = true;
      }
    }
  }
}

class FirstLogicHumanNotMid extends Logic
{
  GameManager GMR;

  HumanFirstOnLogic father;

  FirstLogicHumanNotMid(GameManager _GMR, HumanFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;
  }

  public void Update()
  {
    if (GMR.turnCount == 1)
    {
      if (GMR.resource.r_pane.CheckInCorner(GMR.resource.r_pane.lastSelectedRect))
      {
        father.ChangeLogic(new HumanNotMidCorner(GMR, father));
      } else
      {
        father.ChangeLogic(new HumanNotMidNearMid(GMR, father));
      }
    }
  }
}

class FirstLogicHumanMid extends Logic
{
  GameManager GMR;

  HumanFirstOnLogic father;

  boolean humanSelectedMid;

  Pane.PaneRect firstCha;

  Pane.PaneRect secondCha;

  Pane.PaneRect thirdCha;

  boolean stepThreeLock;

  boolean stepFiveLock1;

  boolean stepFiveLock2;  

  FirstLogicHumanMid(GameManager _GMR, HumanFirstOnLogic _father)
  {
    GMR = _GMR;

    father = _father;

    stepThreeLock = false;

    stepFiveLock1 = false;

    stepFiveLock2 = false;
  }

  public void Update()
  {
    if (GMR.turnCount == 1)
    {
      if (GMR.resource.lastTurnIsInPaneMid())
      { 
        //println("haha");
        GMR.resource.SetPaneRectRandomCorner(false);
        GMR.turnCount++;

        firstCha = GMR.resource.r_pane.lastCompSelectedRect;
      }
      else
      {
        father.ChangeLogic(new FirstLogicHumanNotMid(GMR, father));
      }
    }

    if (GMR.turnCount == 3)
    {
      if (GMR.resource.lastTurnIsDuiCorner())
      {
        GMR.resource.SetPaneRectRandomCorner(false);
        GMR.turnCount++;

        secondCha = GMR.resource.r_pane.lastCompSelectedRect;

        stepThreeLock = true;
      }
      else 
      {
        GMR.resource.r_pane.SetInversePosition(false);

        secondCha = GMR.resource.r_pane.lastCompSelectedRect;

        GMR.turnCount++;
      }
    }

    if (GMR.turnCount == 5)
    {
      if (stepThreeLock)
      {
        //divide into two
        if (GMR.resource.r_pane.InTheMidOfTwoRect(firstCha, secondCha))
        {
          //set in the inverse position
          GMR.resource.r_pane.SetInversePosition(false);
          GMR.turnCount++;
        } 
        else
        {
          GMR.resource.r_pane.SetMidRectGouOrCha(firstCha, secondCha, false);
          GMR.turnCount++;
          GMR.compuWin = true;
        }
      } else
      {
        if (!GMR.resource.r_pane.CheckSameRowOrSameColumn(firstCha, secondCha))
        {
          if (!GMR.resource.r_pane.CheckIsLastTurnInDuiCorner(firstCha))
          {
            GMR.resource.r_pane.SetInversePosition(false);
            GMR.turnCount++;

            thirdCha = GMR.resource.r_pane.lastCompSelectedRect;
          } else
          {
            GMR.resource.r_pane.SetRandomPosition(false);
            GMR.turnCount++;

            thirdCha = GMR.resource.r_pane.lastCompSelectedRect;
          }

          stepFiveLock1 = true;
        } else
        {
          if (GMR.resource.r_pane.CheckCanWinTwoSubOne(firstCha, secondCha, false))
          {
            GMR.turnCount++;
            GMR.compuWin = true;
          } else
          {
            GMR.resource.r_pane.SetInversePosition(false);
            GMR.turnCount++;
          }
          stepFiveLock2 = true;
        }
      }
    }

    if (GMR.turnCount == 7)
    {
      if (stepThreeLock)
      {
        if (GMR.resource.r_pane.CheckLastTurnInNearMid())
        {
          GMR.resource.r_pane.SetInversePosition(false);
          GMR.turnCount++;
        } else //set in the corner
        {
          GMR.resource.r_pane.SetRandomPosition(false);
          GMR.turnCount++;
        }
      } else if (stepFiveLock1)
      {
        if (GMR.resource.r_pane.CheckCanWinTwoSubOne(firstCha, thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else if (GMR.resource.r_pane.CheckCanWinTwoSubOne(secondCha, thirdCha, false))
        {
          GMR.turnCount++;
          GMR.compuWin = true;
        } else
        {
          GMR.resource.r_pane.SetLastTurnInverseOrRandom(false);
          GMR.turnCount++;
        }
      } else if (stepFiveLock2)
      {
        GMR.resource.r_pane.SetLastTurnInverseOrRandom(false);
        GMR.turnCount++;
      }
    }

    if (GMR.turnCount == 9)
    {
      if(GMR.compuWin)
      {
        return;
      }
      
      if (stepThreeLock)
      {
        //must draw
        GMR.draw = true;
      } else if (stepFiveLock1)
      {
        GMR.draw = true;
      } else if (stepFiveLock2)
      {
        GMR.draw = true;
      }
    }
  }
}

class HumanFirstOnLogic
{
  GameManager GMR;

  Logic logic;

  HumanFirstOnLogic(GameManager _GMR)
  {
    GMR = _GMR;

    logic = new FirstLogicHumanMid(GMR, this);
  }

  public void ChangeLogic(Logic _logic)
  {
    logic = _logic;
    logic.Update();
  }

  public void Update()
  {
    logic.Update();
  }
}
class Pane
{
  class PaneRect
  {
    PVector location;
    float size;

    int oldColor;
    int newColor;

    boolean gou;
    boolean cha;

    int i;
    int j;

    PaneRect(PVector _location, float _size, int _i, int _j)
    {
      location = _location;
      size = _size;

      oldColor = 0xffF7ACAC;
      newColor = 0xffDEC7C7;

      gou = false;
      cha = false;

      i = _i;
      j = _j;
    }

    public boolean IsInRect()
    {
      return (abs(mouseX - location.x) <= size/2.0f) && (abs(mouseY - location.y) <= size/2.0f);
    }

    public void CheckIfMouseOverAndChangeColor()
    {
      if (IsInRect())
      {
        fill(newColor, 1);
      } else
      {
        fill(oldColor, 100);
      }
    }

    public void Draw()
    {
      rect(location.x, location.y, size, size);

      if (gou)
      {
        pushStyle();
        textSize(60);
        strokeWeight(40);
        fill(0xff29E30E);
        text("\u221a", location.x, location.y, size, size);
        popStyle();
      } else if (cha)
      {
        pushStyle();
        textSize(100);
        strokeWeight(80);
        fill(0xffFA0808);
        text("\u00d7", location.x, location.y, size, size);
        popStyle();
      }
    }

    public void Update()
    {
      pushStyle();
      CheckIfMouseOverAndChangeColor(); 
      Draw();
      popStyle();
    }
  }

  PaneRect[][]  paneRects;

  float tempFirstLocation;
  float size;

  PaneRect lastSelectedRect;

  PaneRect lastCompSelectedRect;

  Pane()
  {
    paneRects = new PaneRect[3][3];

    size = width/3.0f;

    tempFirstLocation = width/2.0f - size;

    PVector firstAnchor = new PVector(tempFirstLocation, tempFirstLocation);

    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      { 
        PVector tempVector = new PVector(j * size, i * size);
        paneRects[i][j] = new PaneRect(PVector.add(firstAnchor, tempVector), size, i, j);
      }
    }
  }

  public boolean SetGouOrChaForMouse(float x, float y, boolean gouOrCha)
  {
    float i = (abs(x - tempFirstLocation)/size);
    float j = (abs(y - tempFirstLocation)/size);

    int iTemp = round(i);
    int jTemp = round(j);

    if (gouOrCha)
    {
      if (paneRects[jTemp][iTemp].gou || paneRects[jTemp][iTemp].cha)
      {
        return false;
      }
      paneRects[jTemp][iTemp].gou = true;
      lastSelectedRect = paneRects[jTemp][iTemp];

      return true;
    } else
    {
      if (paneRects[jTemp][iTemp].cha || paneRects[jTemp][iTemp].gou)
      {
        return false;
      }      
      paneRects[jTemp][iTemp].cha = true;
      lastSelectedRect = paneRects[jTemp][iTemp];

      return true;
    }
  }

  public void SetGouOrCha(int i, int j, boolean gouOrCha)
  {
    if (gouOrCha)
    {
      paneRects[i][j].gou = true;
    } else
    {
      paneRects[i][j].cha = true;
    }

    lastCompSelectedRect = paneRects[i][j];
  }

  public void SetRandomCorner(boolean gouOrCha)
  {
    int[][] tempCorner = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};

    int temp = PApplet.parseInt(random(0, 4));

    //println(temp);
    int i = tempCorner[temp][0];
    int j = tempCorner[temp][1];

    while (paneRects[i][j].gou || paneRects[i][j].cha)
    {
      temp = (temp + 1) % 4;
      i = tempCorner[temp][0];
      j = tempCorner[temp][1];
    }

    SetGouOrCha(i, j, gouOrCha);
  }

  public void SetRandomNearMid(boolean gouOrCha)
  {
    int[][] tempCorner = new int[][]{{0, 1}, {1, 2}, {2, 1}, {1, 2}};

    int temp = PApplet.parseInt(random(0, 4));

    //println(temp);
    int i = tempCorner[temp][0];
    int j = tempCorner[temp][1];

    while (paneRects[i][j].gou || paneRects[i][j].cha)
    {
      temp = (temp + 1) % 4;
      i = tempCorner[temp][0];
      j = tempCorner[temp][1];
    }

    SetGouOrCha(i, j, gouOrCha);
  }

  public void CheckInput()
  {
  }

  public void Logic()
  {
    CheckInput();
  }

  public boolean CheckIsLastTurnInMid()
  {
    return lastSelectedRect == paneRects[1][1];
  }

  public boolean CheckIsLastTurnInDuiCorner()
  {
    int i = 2-lastSelectedRect.i;
    int j = 2-lastSelectedRect.j;

    return paneRects[i][j].cha == true;
  }

  public boolean CheckIsLastTurnInDuiCorner(PaneRect r)
  {
    int i = r.i;
    int j = r.j;

    return ((lastSelectedRect.i + i) == 2) && ((lastSelectedRect.j + j) == 2);
  }

  public boolean CheckInCorner(PaneRect r)
  {
    int i = r.i;
    int j = r.j;

    return (i==0&&j==0) || (i==0&&j==2) || (i==2&&j==0) || (i==2&&j==2);
  }

  public boolean CheckLastTurnInCorner()
  {
    int i = lastSelectedRect.i;
    int j = lastSelectedRect.j;

    return (i==0&&j==0) || (i==0&&j==2) || (i==2&&j==0) || (i==2&&j==2);
  }

  public boolean CheckLastTurnInNearMid()
  {
    int i = lastSelectedRect.i;
    int j = lastSelectedRect.j;

    return (i==0&&j==1) || (i==1&&j==0) || (i==2&&j==1) || (i==1&&j==2);
  }

  public boolean CheckSameRowOrSameColumn(PaneRect r)
  { 
    return (r.i == lastSelectedRect.i) || (r.j == lastSelectedRect.j);
  }

  public boolean CheckSameRowOrSameColumn(PaneRect f, PaneRect s)
  { 
    return (f.i == s.i) || (f.j == s.j);
  }

  public boolean CheckCanWinTwoSubOne(PaneRect f, PaneRect s,boolean gouOrCha)
  {
    if (f.i == s.i)
    {
      for (int jTemp = 0; jTemp < 3; jTemp++)
      {
        if (!paneRects[f.i][jTemp].gou && !paneRects[f.i][jTemp].cha)
        {
          SetGouOrCha(f.i, jTemp, gouOrCha);
          return true;
        }
      }
    } 
    else if (f.j == s.j)
    {
      for (int iTemp = 0; iTemp < 3; iTemp++)
      {
        if (!paneRects[iTemp][f.j].gou && !paneRects[iTemp][f.j].cha)
        {
          SetGouOrCha(iTemp, f.j, gouOrCha);
          return true;
        }
      }
    }
    
    return false;
  }

  public void Draw()
  {
    stroke(0xff361E71);
    strokeWeight(4);
    //fill(#F54F70, 1);

    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        paneRects[i][j].Update();
      }
    }
  }

  public boolean InTheMidOfTwoRect(PaneRect f, PaneRect s)
  {
    return ((f.i + s.i)/2 == lastSelectedRect.i) && ((f.j + s.j)/2 == lastSelectedRect.j);
  }

  public void SetMidRectGouOrCha(PaneRect f, PaneRect s, boolean gouOrCha)
  {
    SetGouOrCha((f.i + s.i)/2, (f.j + s.j)/2, gouOrCha);
  }
  
  public boolean SetMidRectGouOrCha(PaneRect f, PaneRect s)
  {
    int iTemp =  (f.i + s.i)/2;
    int jTemp =  (f.j + s.j)/2;
    
    if(!paneRects[iTemp][jTemp].gou && !paneRects[iTemp][jTemp].cha)
    {
      SetGouOrCha(iTemp, jTemp, false);
      return true;
    }
    
    return false;
  }
  
  public void SetInversePosition(boolean gouOrCha)
  {
    SetGouOrCha((2 - lastSelectedRect.i), (2 - lastSelectedRect.j), gouOrCha);
  }

  public void SetRandomPosition(boolean gouOrCha)
  {
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        if (!paneRects[i][j].gou && !paneRects[i][j].cha)
        {
          SetGouOrCha(i, j, gouOrCha);
          return;
        }
      }
    }
  }

  public void SetMidPosition(boolean gouOrCha)
  {
    SetGouOrCha(1, 1, gouOrCha);
  }

  public void SetInsertRowOrColumn(PaneRect r, boolean gouOrCha)
  {
    if (r.i == lastSelectedRect.i)
    {
      for (int jTemp = 0; jTemp < 3; jTemp++)
      {
        if (!paneRects[r.i][jTemp].gou && !paneRects[r.i][jTemp].cha)
        {
          SetGouOrCha(r.i, jTemp, gouOrCha);
          return;
        }
      }
    } else if (r.j == lastSelectedRect.j)
    {
      for (int iTemp = 0; iTemp < 3; iTemp++)
      {
        if (!paneRects[iTemp][r.j].gou && !paneRects[iTemp][r.j].cha)
        {
          SetGouOrCha(iTemp, r.j, gouOrCha);
          return;
        }
      }
    }
  }
  
  public boolean SetInsertRowOrColumn(PaneRect f,PaneRect s, boolean gouOrCha)
  {
    if (f.i == s.i)
    {
      for (int jTemp = 0; jTemp < 3; jTemp++)
      {
        if (!paneRects[f.i][jTemp].gou && !paneRects[f.i][jTemp].cha)
        {
          SetGouOrCha(f.i, jTemp, gouOrCha);
          return true;
        }
      }
    }
    else if (f.j == s.j)
    {
      for (int iTemp = 0; iTemp < 3; iTemp++)
      {
        if (!paneRects[iTemp][s.j].gou && !paneRects[iTemp][s.j].cha)
        {
          SetGouOrCha(iTemp, s.j, gouOrCha);
          return true;
        }
      }
    }   
    return false;
  }
  
  public void SetLastTurnInverseOrRandom(boolean gouOrCha)
  {
    if((!paneRects[2 - lastSelectedRect.i][2 - lastSelectedRect.j].gou) && (!paneRects[2 - lastSelectedRect.i][2 - lastSelectedRect.j].cha))
    {
      SetGouOrCha((2 - lastSelectedRect.i), (2 - lastSelectedRect.j), gouOrCha);
    }
    else
    {
      SetRandomPosition(gouOrCha);
    }
  }
  
  public boolean CheckIsDuiCornerGou(PaneRect r)
  {
    return paneRects[2 - r.i][2 - r.j].gou == true;
  }
  
  public boolean CheckInverseAndSet(PaneRect r,boolean gouOrCha)
  {
    int inverseI = 2 - r.i;
    int inverseJ = 2 - r.j;
    
    if(!paneRects[inverseI][inverseJ].gou && !paneRects[inverseI][inverseJ].cha)
    {
      SetGouOrCha(inverseI, inverseJ, gouOrCha);
      return true;
    }
    
    return false;
  }
   
  public void SetSpecialPosition(PaneRect f,PaneRect s)
  {
    if(CheckSameRowOrSameColumn(paneRects[f.i][2-f.j],s))
    {
      SetGouOrCha(f.i, 2-f.j, false);
    }
    else
    {
      SetGouOrCha(2-f.i, f.j, false);
    }
  }
  
  public void SetSpecialPositionForCorner(PaneRect f,PaneRect s)
  {
    int[][] tempCorner = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};
    
    for(int iter = 0 ;iter < 4;iter++)
    {
      //println(temp);
      int i = tempCorner[iter][0];
      int j = tempCorner[iter][1];  
      
      if(!paneRects[i][j].gou && !paneRects[i][j].cha)
      {
        if(CheckSameRowOrSameColumn(paneRects[i][j],f) && CheckSameRowOrSameColumn(paneRects[i][j],s))
        {
          SetGouOrCha(i,j,false);
        }        
      }
    }
  }
  
  public void SetSpecialPositionForCorner1(PaneRect f,PaneRect s)
  {
    int[][] tempCorner = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};
    
    for(int iter = 0 ;iter < 4;iter++)
    {
      //println(temp);
      int i = tempCorner[iter][0];
      int j = tempCorner[iter][1];  
      
      if(!paneRects[i][j].gou && !paneRects[i][j].cha)
      {
        if(!(CheckSameRowOrSameColumn(paneRects[i][j],f) && CheckSameRowOrSameColumn(paneRects[i][j],s)))
        {
          SetGouOrCha(i,j,false);
        }        
      }
    }
  }
  
  public void SetSpecialRandomCorner(PaneRect r,boolean gouOrCha)
  {
    int[][] tempCorner = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};
    
    for(int iter = 0 ;iter < 4;iter++)
    {
      //println(temp);
      int i = tempCorner[iter][0];
      int j = tempCorner[iter][1];  
      
      if(!paneRects[i][j].gou && !paneRects[i][j].cha)
      {
        if(CheckSameRowOrSameColumn(paneRects[i][j],r))
        {
          SetGouOrCha(i,j,false);
          return;
        }        
      }
    }
  }
  
  public void Update()
  {
    pushStyle();
    Logic();
    Draw();
    popStyle();
  }
}
class Resource
{
  Pane r_pane;

  Resource()
  {    
    r_pane = new Pane();
  }

  public void HumanFirstOnUpdate()
  {        
    r_pane.Update();
  }
  
  public boolean SetPaneRectGouOrChaForMouse(float x,float y,boolean gouOrCha)
  {
    return r_pane.SetGouOrChaForMouse(x,y,gouOrCha);
  }
  
  public void SetPaneRectGouOrCha(int i,int j,boolean gouOrCha)
  {
    r_pane.SetGouOrCha(i,j,gouOrCha);
  }
  
  public void Update()
  {
    r_pane.Update();
  }
 
  public void SetPaneRectRandomCorner(boolean gouOrCha)
  {
    r_pane.SetRandomCorner(gouOrCha);
  } 
 
  public boolean lastTurnIsInPaneMid()
  {
    return r_pane.CheckIsLastTurnInMid();
  }
  
  public boolean lastTurnIsDuiCorner()
  {
    return r_pane.CheckIsLastTurnInDuiCorner();
  }
}
class UI
{
  class Button
  {
    PVector location;
    float xSize;
    float ySize;
    float cornerSize;

    String string;

    int oldColor;
    int newColor;

    boolean isClicked;
    
    boolean soundLock;
    
    Button(PVector _location, float _xSize, String _string)
    {
      location = _location;
      xSize = _xSize;
      ySize = xSize/2.0f;
      cornerSize = xSize/4.0f;

      oldColor = 0xffF36B6B;
      newColor = 0xffECE58A;

      string = _string;

      isClicked = false;
      
      soundLock  = false;
    }

    public void Draw()
    {       
      rect(location.x, location.y, xSize, ySize, cornerSize);
      pushStyle();
      fill(0xff1FB57B);
      strokeWeight(6);
      textSize(14);
      text(string, location.x, location.y);
      popStyle();
    }

    public boolean IsInRect()
    {
      return (abs(mouseX - location.x) <= xSize/2.0f) && (abs(mouseY - location.y) <= ySize/2.0f);
    }

    public void CheckIfMouseOverAndChangeColor()
    {
      if (IsInRect())
      {
        fill(newColor);
      } 
      else
      {
        fill(oldColor);
      }
    }

    public void Update()
    {
      if(IsInRect() && !soundLock)
      {
        
        clip.play();
        
        soundLock = true;
      }
      
      if(!IsInRect())
      {
        clip.rewind();
        soundLock = false;
      }
      
      pushStyle();
      CheckIfMouseOverAndChangeColor(); 
      Draw();
      popStyle();
    }

    public void MouseListener()
    {
      if (IsInRect())
      {
        isClicked = true;
      }
    }
  }

  PImage background;
  PFont font;

  Button humanFirst;
  Button compuFirst;
  
  Button restart;
  
  float tempLoacation;

  boolean ShowBeginUILock;
  boolean ShowGameOnUILock;

  boolean isHumanFirst;
  boolean isCompuFirst;
  
  boolean isRestart;
  
  UI()
  {
    background = loadImage("data/background.PNG");
    font = loadFont("Lolita-48.vlw");

    tempLoacation = width/2.0f;

    humanFirst = new Button(new PVector(tempLoacation, tempLoacation), 90, "Human First");
    compuFirst = new Button(new PVector(tempLoacation, tempLoacation * 1.5f), 90, "Compu First");
    restart = new Button(new PVector(tempLoacation, tempLoacation * 1.25f), 90, "Restart");
    
    isHumanFirst = false;
    isCompuFirst = false;
    
    isRestart = false;
    
    ShowBeginUILock = false;
    ShowGameOnUILock = false;

    imageMode(CENTER);
    textFont(font, 50);
    textAlign(CENTER, CENTER);
  }

  public void ShowBeginUI()
  {
    if (!ShowBeginUILock)
    {
      image(background, tempLoacation, tempLoacation, width, height);
      pushStyle();
      fill(0xffFAF321);
      strokeWeight(6);
      text("Tic-tac-toe", width/2.0f, height/4.0f);
      popStyle();
      ShowBeginUILock = true;
    }

    humanFirst.Update();
    compuFirst.Update();

    if (humanFirst.isClicked)
    {
      isHumanFirst = true;
      return;
    }

    if (compuFirst.isClicked)
    {
      isCompuFirst = true;
      return;
    }
  }

  public void ShowGameOnUI()
  {
    image(background, tempLoacation, tempLoacation, width, height);
    ShowGameOnUILock = true;
  }

  public void ShowGameOverComputerWIn()
  {
    pushStyle();
    fill(0xff0831FA);
    textSize(40);
    strokeWeight(20);
    text("COMPUTER WIN", width/2.0f, height/4.0f,width,height);
    popStyle();
    
    restart.Update();
    
    if (restart.isClicked)
    {
      isRestart = true;
    }
  }
  
  public void ShowGameOverDraw()
  {
    pushStyle();
    fill(0xffF07D11);
    textSize(40);
    strokeWeight(20);
    text("Game Draw", width/2.0f, height/4.0f,width,height);
    popStyle();
    
    restart.Update();
    
    if (restart.isClicked)
    {
      isRestart = true;
    }
  }
}
  public void settings() {  size(300, 300); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NewTestJingziqiV2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
