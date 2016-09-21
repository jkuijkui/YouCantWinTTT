class Pane
{
  class PaneRect
  {
    PVector location;
    float size;

    color oldColor;
    color newColor;

    boolean gou;
    boolean cha;

    int i;
    int j;

    PaneRect(PVector _location, float _size, int _i, int _j)
    {
      location = _location;
      size = _size;

      oldColor = #F7ACAC;
      newColor = #DEC7C7;

      gou = false;
      cha = false;

      i = _i;
      j = _j;
    }

    boolean IsInRect()
    {
      return (abs(mouseX - location.x) <= size/2.0f) && (abs(mouseY - location.y) <= size/2.0f);
    }

    void CheckIfMouseOverAndChangeColor()
    {
      if (IsInRect())
      {
        fill(newColor, 1);
      } else
      {
        fill(oldColor, 100);
      }
    }

    void Draw()
    {
      rect(location.x, location.y, size, size);

      if (gou)
      {
        pushStyle();
        textSize(60);
        strokeWeight(40);
        fill(#29E30E);
        text("√", location.x, location.y, size, size);
        popStyle();
      } else if (cha)
      {
        pushStyle();
        textSize(100);
        strokeWeight(80);
        fill(#FA0808);
        text("×", location.x, location.y, size, size);
        popStyle();
      }
    }

    void Update()
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

  boolean SetGouOrChaForMouse(float x, float y, boolean gouOrCha)
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

  void SetGouOrCha(int i, int j, boolean gouOrCha)
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

  void SetRandomCorner(boolean gouOrCha)
  {
    int[][] tempCorner = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};

    int temp = int(random(0, 4));

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

  void SetRandomNearMid(boolean gouOrCha)
  {
    int[][] tempCorner = new int[][]{{0, 1}, {1, 2}, {2, 1}, {1, 2}};

    int temp = int(random(0, 4));

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

  void CheckInput()
  {
  }

  void Logic()
  {
    CheckInput();
  }

  boolean CheckIsLastTurnInMid()
  {
    return lastSelectedRect == paneRects[1][1];
  }

  boolean CheckIsLastTurnInDuiCorner()
  {
    int i = 2-lastSelectedRect.i;
    int j = 2-lastSelectedRect.j;

    return paneRects[i][j].cha == true;
  }

  boolean CheckIsLastTurnInDuiCorner(PaneRect r)
  {
    int i = r.i;
    int j = r.j;

    return ((lastSelectedRect.i + i) == 2) && ((lastSelectedRect.j + j) == 2);
  }

  boolean CheckInCorner(PaneRect r)
  {
    int i = r.i;
    int j = r.j;

    return (i==0&&j==0) || (i==0&&j==2) || (i==2&&j==0) || (i==2&&j==2);
  }

  boolean CheckLastTurnInCorner()
  {
    int i = lastSelectedRect.i;
    int j = lastSelectedRect.j;

    return (i==0&&j==0) || (i==0&&j==2) || (i==2&&j==0) || (i==2&&j==2);
  }

  boolean CheckLastTurnInNearMid()
  {
    int i = lastSelectedRect.i;
    int j = lastSelectedRect.j;

    return (i==0&&j==1) || (i==1&&j==0) || (i==2&&j==1) || (i==1&&j==2);
  }

  boolean CheckSameRowOrSameColumn(PaneRect r)
  { 
    return (r.i == lastSelectedRect.i) || (r.j == lastSelectedRect.j);
  }

  boolean CheckSameRowOrSameColumn(PaneRect f, PaneRect s)
  { 
    return (f.i == s.i) || (f.j == s.j);
  }

  boolean CheckCanWinTwoSubOne(PaneRect f, PaneRect s,boolean gouOrCha)
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

  void Draw()
  {
    stroke(#361E71);
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

  boolean InTheMidOfTwoRect(PaneRect f, PaneRect s)
  {
    return ((f.i + s.i)/2 == lastSelectedRect.i) && ((f.j + s.j)/2 == lastSelectedRect.j);
  }

  void SetMidRectGouOrCha(PaneRect f, PaneRect s, boolean gouOrCha)
  {
    SetGouOrCha((f.i + s.i)/2, (f.j + s.j)/2, gouOrCha);
  }
  
  boolean SetMidRectGouOrCha(PaneRect f, PaneRect s)
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
  
  void SetInversePosition(boolean gouOrCha)
  {
    SetGouOrCha((2 - lastSelectedRect.i), (2 - lastSelectedRect.j), gouOrCha);
  }

  void SetRandomPosition(boolean gouOrCha)
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

  void SetMidPosition(boolean gouOrCha)
  {
    SetGouOrCha(1, 1, gouOrCha);
  }

  void SetInsertRowOrColumn(PaneRect r, boolean gouOrCha)
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
  
  boolean SetInsertRowOrColumn(PaneRect f,PaneRect s, boolean gouOrCha)
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
  
  void SetLastTurnInverseOrRandom(boolean gouOrCha)
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
  
  boolean CheckIsDuiCornerGou(PaneRect r)
  {
    return paneRects[2 - r.i][2 - r.j].gou == true;
  }
  
  boolean CheckInverseAndSet(PaneRect r,boolean gouOrCha)
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
   
  void SetSpecialPosition(PaneRect f,PaneRect s)
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
  
  void SetSpecialPositionForCorner(PaneRect f,PaneRect s)
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
  
  void SetSpecialPositionForCorner1(PaneRect f,PaneRect s)
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
  
  void SetSpecialRandomCorner(PaneRect r,boolean gouOrCha)
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
  
  void Update()
  {
    pushStyle();
    Logic();
    Draw();
    popStyle();
  }
}