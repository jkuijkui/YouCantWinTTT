abstract class Logic
{
  abstract void Update();
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

  void Update()
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

  void Update()
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

  void Update()
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

  void Update()
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

  void ChangeLogic(Logic _logic)
  {
    logic = _logic;
    logic.Update();
  }

  void Update()
  {
    logic.Update();
  }
}