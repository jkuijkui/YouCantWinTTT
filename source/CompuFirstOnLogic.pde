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

  void Update()
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

  void Update()
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

  void Update()
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