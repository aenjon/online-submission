package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 22:45
 * Version: 1.0
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "os_log", schema = "online_submission")
public class OsLog {
    private int id;
    private String content;
    private String userOrAdminName;
    private Date time;
    private String ip;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "user_or_admin_name", nullable = true, length = 255)
    public String getUserOrAdminName() {
        return userOrAdminName;
    }

    public void setUserOrAdminName(String userOrAdminName) {
        this.userOrAdminName = userOrAdminName;
    }

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    @Column(name = "time", nullable = true)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "ip", nullable = true, length = 255)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsLog osLog = (OsLog) o;

        if (id != osLog.id) return false;
        if (content != null ? !content.equals(osLog.content) : osLog.content != null) return false;
        if (userOrAdminName != null ? !userOrAdminName.equals(osLog.userOrAdminName) : osLog.userOrAdminName != null)
            return false;
        if (time != null ? !time.equals(osLog.time) : osLog.time != null) return false;
        return ip != null ? ip.equals(osLog.ip) : osLog.ip == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (userOrAdminName != null ? userOrAdminName.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}