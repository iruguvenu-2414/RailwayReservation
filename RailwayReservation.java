import java.util.*;

public class RailwayReservation {
    static List<Train> trains = new ArrayList<>();
    static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        initializeTrains();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Forgot Password\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    forgotPassword(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Initialize train details
    public static void initializeTrains() {
        trains.add(new Train("Vande Bharat", "Secunderabad to Tirupathi"));
        trains.add(new Train("Krishna Express", "Secunderabad to Tirupathi"));
        trains.add(new Train("Sabari Express", "Secunderabad to Tirupathi"));
        trains.add(new Train("Rayalaseema Express", "Secunderabad to Tirupathi"));
        trains.add(new Train("Venkatadri Express", "Secunderabad to Tirupathi"));
    }

    // User registration
    public static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please try another.");
        } else {
            users.put(username, new User(username, password, email));
            System.out.println("Registration successful.");
        }
    }

    // User login
    public static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            System.out.println("Login successful.");
            userMenu(scanner, users.get(username));
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    // Forgot password
    public static void forgotPassword(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).email.equals(email)) {
            System.out.println("Your password is: " + users.get(username).password);
        } else {
            System.out.println("Invalid username or email.");
        }
    }

    // User menu
    public static void userMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("\n1. Book Seat\n2. Cancel Booking\n3. Check Seat Availability\n4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bookSeat(scanner, user);
                    break;
                case 2:
                    cancelBooking(user);
                    break;
                case 3:
                    checkSeatAvailability();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Book a seat
    public static void bookSeat(Scanner scanner, User user) {
        if (user.bookedTrain != null) {
            System.out.println("You have already booked a seat in " + user.bookedTrain.name + ".");
            return;
        }

        System.out.println("Available trains:");
        List<Train> availableTrains = new ArrayList<>();

        for (Train train : trains) {
            if (train.hasAvailableSeats()) {
                availableTrains.add(train);
            }
        }

        if (availableTrains.isEmpty()) {
            System.out.println("No available seats.");
            return;
        }

        for (int i = 0; i < availableTrains.size(); i++) {
            System.out.println((i + 1) + ". " + availableTrains.get(i).name);
        }

        System.out.print("Select train (1-" + availableTrains.size() + "): ");
        int trainChoice = scanner.nextInt();
        scanner.nextLine();

        if (trainChoice < 1 || trainChoice > availableTrains.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Train selectedTrain = availableTrains.get(trainChoice - 1);
        selectedTrain.displayAvailableSeats();

        System.out.print("Select seat (Enter seat number): ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine();

        if (selectedTrain.bookSeat(seatNumber)) {
            user.bookedTrain = selectedTrain;
            user.seatNumber = seatNumber;
            System.out.println("Seat booked successfully in " + selectedTrain.name + ". Seat Number: " + seatNumber);
        } else {
            System.out.println("Invalid or already booked seat.");
        }
    }

    // Cancel booking
    public static void cancelBooking(User user) {
        if (user.bookedTrain == null) {
            System.out.println("You have no booking to cancel.");
            return;
        }

        Train train = user.bookedTrain;
        train.cancelSeat(user.seatNumber);
        System.out.println("Booking canceled for " + train.name + ". Seat Number: " + user.seatNumber);

        user.bookedTrain = null;
        user.seatNumber = -1;
    }

    // Check seat availability
    public static void checkSeatAvailability() {
        for (Train train : trains) {
            System.out.println(train.name + " - Available Seats: " + train.getAvailableSeatsCount());
        }
    }
}
