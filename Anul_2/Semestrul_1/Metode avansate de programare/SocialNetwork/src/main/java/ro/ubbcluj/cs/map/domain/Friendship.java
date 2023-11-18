package ro.ubbcluj.cs.map.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Long> {
    private Long user1_id;
    private Long user2_id;

     LocalDateTime friends_from;

    public Friendship(Long user1_id, Long user2_id) {
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        friends_from = LocalDateTime.now();
    }

    public Friendship(User user1, User user2) {
        this.user1_id = user1.getId();
        this.user2_id = user2.getId();
        friends_from = LocalDateTime.now();
    }

    public Friendship(Long user1_id, Long user2_id, LocalDateTime friends_from) {
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.friends_from = friends_from;
    }



    public Long getUser1_id() {
        return user1_id;
    }

    public void setUser1_id(Long user1_id) {
        this.user1_id = user1_id;
    }

    public Long getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(Long user2_id) {
        this.user2_id = user2_id;
    }

    public LocalDateTime getFriends_from() {
        return friends_from;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "user1_id=" + user1_id +
                ", user2_id=" + user2_id +
                ", friends_from=" + friends_from +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(user1_id, that.user1_id) && Objects.equals(user2_id, that.user2_id) ||
                Objects.equals(user1_id, that.user2_id) && Objects.equals(user2_id, that.user1_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1_id, user2_id);
    }
}
