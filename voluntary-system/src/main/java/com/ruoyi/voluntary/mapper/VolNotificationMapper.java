package com.ruoyi.voluntary.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voluntary.domain.VolNotification;

/**
 * 业务通知 Mapper
 */
public interface VolNotificationMapper
{
    VolNotification selectVolNotificationById(Long id);

    List<VolNotification> selectVolNotificationList(VolNotification notification);

    int countUnreadByReceiverUserId(Long receiverUserId);

    int insertVolNotification(VolNotification notification);

    int updateVolNotification(VolNotification notification);

    int markReadByIdAndReceiverUserId(@Param("id") Long id, @Param("receiverUserId") Long receiverUserId,
            @Param("updateBy") String updateBy);

    int markAllReadByReceiverUserId(@Param("receiverUserId") Long receiverUserId, @Param("updateBy") String updateBy);
}
