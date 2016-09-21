class Resource
{
  Pane r_pane;

  Resource()
  {    
    r_pane = new Pane();
  }

  void HumanFirstOnUpdate()
  {        
    r_pane.Update();
  }
  
  boolean SetPaneRectGouOrChaForMouse(float x,float y,boolean gouOrCha)
  {
    return r_pane.SetGouOrChaForMouse(x,y,gouOrCha);
  }
  
  void SetPaneRectGouOrCha(int i,int j,boolean gouOrCha)
  {
    r_pane.SetGouOrCha(i,j,gouOrCha);
  }
  
  void Update()
  {
    r_pane.Update();
  }
 
  void SetPaneRectRandomCorner(boolean gouOrCha)
  {
    r_pane.SetRandomCorner(gouOrCha);
  } 
 
  boolean lastTurnIsInPaneMid()
  {
    return r_pane.CheckIsLastTurnInMid();
  }
  
  boolean lastTurnIsDuiCorner()
  {
    return r_pane.CheckIsLastTurnInDuiCorner();
  }
}