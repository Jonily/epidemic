package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuwei
 * @since 2021-05-06
 */
public class RoomInfo extends Model<RoomInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "room_id", type = IdType.UUID)
    private Integer roomId;

    private String roomType;

    private Integer roomStatus;

    private Double roomPrice;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }
    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    protected Serializable pkVal() {
        return this.roomId;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
            "roomId=" + roomId +
            ", roomType=" + roomType +
            ", roomStatus=" + roomStatus +
            ", roomPrice=" + roomPrice +
        "}";
    }
}
