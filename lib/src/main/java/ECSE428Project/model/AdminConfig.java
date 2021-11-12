package ECSE428Project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "adminConfig")
public class AdminConfig {
  @Id
  private String name;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<String> bannedEmails = new ArrayList<String>();
  @ElementCollection(fetch = FetchType.LAZY)
  private List<String> adminEmails = new ArrayList<String>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getBannedEmails() {
    return Collections.unmodifiableList(bannedEmails);
  }

  public boolean addBannedEmail(String bannedEmail) {
    if (bannedEmails.contains(bannedEmail)) {
      return false;
    } else {
      bannedEmails.add(bannedEmail);
    }
    return true;
  }

  public boolean removeBannedEmail(String bannedEmail) {
    if (bannedEmails.contains(bannedEmail)) {
      bannedEmails.remove(bannedEmail);
    } else {
      return false;
    }
    return true;
  }

  public List<String> getAdminEmails() {
    return Collections.unmodifiableList(adminEmails);
  }

  public boolean addAdminEmail(String adminEmail) {
    if (adminEmails.contains(adminEmail)) {
      return false;
    } else {
      adminEmails.add(adminEmail);
    }
    return true;
  }

  public boolean removeAdminEmail(String adminEmail) {
    if (adminEmails.contains(adminEmail)) {
      adminEmails.remove(adminEmail);
    } else {
      return false;
    }
    return true;
  }

  public boolean isAdmin(String email) {
    return adminEmails.contains(email);
  }
}
