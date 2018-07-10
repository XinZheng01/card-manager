package com.zx.project.cardmanager.enums;

public enum BankStatus {
  ENABLE(1),
  DISABLE(0);

  private int index;
  BankStatus(int index){
    this.index = index;
  }

  public int getIndex() {
    return index;
  }
}
