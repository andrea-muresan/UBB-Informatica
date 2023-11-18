package ro.ubbcluj.cs.map.service;

import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.repository.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class Service implements ServiceI {
    protected final Repository<Long, User> userRepo;
    protected final Repository<Long, Friendship> friendshipRepo;

    String yellowColorCode = "\u001B[33m";

    String resetColorCode = "\u001B[0m";


    public Service(Repository<Long, User> userRepo, Repository<Long, Friendship> friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;

    }

    @Override
    public boolean addUser(String firstName, String lastName, String email) {
        try {
            if (getUserByEmail(email) != null)
                throw new Exception("The email already exist");

            User u = new User(firstName, lastName, email);
            userRepo.save(u);
            return true;
        } catch (Exception e) {
            System.out.println(yellowColorCode + e.getMessage() + resetColorCode);
            return false;
        }
    }

    @Override
    public boolean deleteUser(String email) {
        try {
            User u = getUserByEmail(email);
            if (u == null)
                throw new Exception("The email does not exist");

            // Delete all user's friendships
            for (Friendship f: friendList(u)) {
                friendshipRepo.delete(f.getId());
            }

            userRepo.delete(u.getId());
            return true;
        } catch (Exception e) {
            System.out.println(yellowColorCode + e.getMessage() + resetColorCode);
            return false;
        }
    }

    @Override
    public boolean createFriendship(String email1, String email2) {
        try {
            if (getFriendshipByEmail(email1, email2) != null)
                throw new Exception("The friendship already exist");

            User u1 = getUserByEmail(email1);
            User u2 = getUserByEmail(email2);

            Friendship f = new Friendship(u1, u2);
            friendshipRepo.save(f);
            return true;
        } catch (Exception e) {
            System.out.println(yellowColorCode + e.getMessage() + resetColorCode);
            return false;
        }
    }

    @Override
    public boolean deleteFriendship(String email1, String email2) {
        try {
            Friendship f = getFriendshipByEmail(email1, email2);
            if (f == null)
                throw new Exception("Friendship not found");

            friendshipRepo.delete(f.getId());
            return true;
        } catch (Exception e) {
            System.out.println(yellowColorCode + e.getMessage() + resetColorCode);
            return false;
        }
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Iterable<Friendship> getAllFriendships() {
        return friendshipRepo.findAll();
    }

    /**
     * dfs algorithm - get the component that includes a given vertex
     * @param user the vertex where we start
     * @param visited set of vertexes already visited
     * @param adjacencyList the adjacency list
     * @param currentComponent - a list where we put the visited vertexes
     */
    private static void dfs(User user, Set<User> visited, Map<User, List<User>> adjacencyList,
                            List<User> currentComponent) {
        visited.add(user);
        currentComponent.add(user);

        for (User friend : adjacencyList.getOrDefault(user, Collections.emptyList())) {
            if (!visited.contains(friend)) {
                dfs(friend, visited, adjacencyList, currentComponent);
            }
        }
    }
    @Override
    public int numberOfCommunities() {
        List<List<User>> communities = getAllCommunities();
        return communities.size();
    }

    @Override
    public List<List<User>> mostSociableCommunity() {
        List<List<User>> communities = getAllCommunities();
        List<List<User>> biggestComponents = new ArrayList<>();
        int maxComponentSize = 0;

        for (List<User> currentComponent : communities) {
            int currentComponentSize = currentComponent.size();
            if (currentComponentSize > maxComponentSize) {
                biggestComponents.clear();
                biggestComponents.add(currentComponent);
                maxComponentSize = currentComponentSize;
            } else if (currentComponentSize == maxComponentSize) {
                biggestComponents.add(currentComponent);
            }
        }

        return biggestComponents;
    }

    @Override
    public List<List<User>> getAllCommunities() {
        Map<User, List<User>> adjacencyList = new HashMap<>();

        // Populate adjacency list based on friendships
        for (Friendship friendship : getAllFriendships()) {
            User u1 = userRepo.findOne(friendship.getUser1_id()).get();
            User u2 = userRepo.findOne(friendship.getUser2_id()).get();
            adjacencyList.computeIfAbsent(u1, k -> new ArrayList<>()).add(u2);
            adjacencyList.computeIfAbsent(u2, k -> new ArrayList<>()).add(u1);
        }

        Set<User> visited = new HashSet<>();
        List<List<User>> components = new ArrayList<>();

        for (User user : getAllUsers()) {
            if (!visited.contains(user)) {
                List<User> currentComponent = new ArrayList<>();
                dfs(user, visited, adjacencyList, currentComponent);
                components.add(currentComponent);
            }
        }

        return components;
    }

    @Override
    public User getUserByEmail(String email) {
        Iterable<User> lst = userRepo.findAll();
        for (User u : lst) {
            if (u.getEmail().equals(email))
                return u;
        }
        return null;
    }

    @Override
    public Friendship getFriendshipByEmail(String email1, String email2) {
        Iterable<Friendship> lst = friendshipRepo.findAll();
        User u1 = getUserByEmail(email1);
        User u2 = getUserByEmail(email2);

        for (Friendship fr : lst) {
            if (fr.equals(new Friendship(u1, u2))) {
                return fr;
            }
        }
        return null;
    }


    @Override
    public ArrayList<Friendship> friendList(User user) {
        Collection<Friendship> friendships = (Collection<Friendship>) friendshipRepo.findAll();
        return friendships.stream()
                .filter(x -> x.getUser1_id().equals(user.getId()) || x.getUser2_id().equals(user.getId())).collect(Collectors.toCollection(ArrayList::new));

    }

    @Override
    public Map<LocalDateTime, User> friendListFrom(User user, Integer month) {
        ArrayList<Friendship> friendList = friendList(user);

        return friendList.stream()
                .filter(f -> f.getFriends_from().getMonthValue() == month)
                .collect(Collectors.toMap(Friendship::getFriends_from, f -> {
                    if (f.getUser1_id().equals(user.getId())) {
                        return userRepo.findOne(f.getUser2_id()).orElseThrow(() -> new RuntimeException("User not found"));
                    } else {
                        return userRepo.findOne(f.getUser1_id()).orElseThrow(() -> new RuntimeException("User not found"));
                    }
                }));
    }

}
