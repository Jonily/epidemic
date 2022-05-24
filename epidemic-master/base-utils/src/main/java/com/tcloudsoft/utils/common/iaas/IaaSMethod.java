package com.tcloudsoft.utils.common.iaas;

public interface IaaSMethod {
  public String PREFIX = "/iaas";
  public String VMCREARE = "create";
  public String INSTANCECREATE = "instanceCreate";
  public String NETWORKCREATE = "network/create";
  public String NETWORKUPDATE = "network/update";
  public String NETWORKDELETE = "network/delete";
  public String POWERON = "vm/powerOn";
  public String REBOOT = "vm/reboot";
  public String SUSPEND = "vm/suspend";
  public String SHUTDOWN = "vm/shutdown";
  public String POWEROFF = "vm/powerOff";
  public String DELETE = "vm/delete";
  public String RESUME = "vm/resume";

  public String RESIZE = "vm/resize";
  public String DISKATTACH = "vm/disk/attach";
  public String CREATEANDDISKDETACH = "vm/disk/createDiskAndAttach";
  public String DISKDETACH = "vm/disk/detach";
  public String DISKRESIZE = "disk/resize";

  public String NETWORKATTACH = "vm/network/attach";
  public String NETWORKDETACH = "vm/network/detach";
  public String MARKASTEMPLATE = "vm/markAsTemplate";
  public String MARKASVIRTUALMACHINE = "vm/markAsVirtualmachine";

  public String SNAPSHOT = "snapshot/create";
  public String RENAMESNAPSHOT = "snapshot/rename";
  public String REVERTTOSNAPSHOT = "snapshot/revert";
  public String REMOVESNAPSHOT = "snapshot/remove";
  public String REMOVEALLSNAPSHOT = "snapshot/removeAll";

  public String SAVEDISKTYPE = "diskType/save";
  public String REMOVEDISKTYPE = "diskType/remove";

  public String REMOVEIMAGE = "image/remove";
  public String ALIYUNINSTANCECREATE = "aliyunInstanceCreate";

  public String TENCENTINSTANCECREATERENEW = "tencentInstanceCreateRenew";
  public String ALIYUNINSTANCECREATERENEW = "aliyunInstanceCreateRenew";

  public String HEALTHCHECK = "healthCheck";

  public String GETALLZONE = "getAllZone";

  public String GETHOSTBYZONE = "getHostByZone";

  public String GETDATASTOREBYZONE = "getDatastoreByZone";

  public String GETIMAGES = "getImages";

  public String GETNETWORKBYZONE = "getNetworkByZone";

  public String GETDATASTOREBYHOST = "getDatastoreByHost";

}

