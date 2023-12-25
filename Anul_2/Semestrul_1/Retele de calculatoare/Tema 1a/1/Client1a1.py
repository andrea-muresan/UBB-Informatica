import socket
import struct

def main():
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1235)

    client_socket.connect(server_address)

    # Read the number of elements from the console
    n = int(input("Enter the number of elements: "))

    # Send the length of the array
    client_socket.send(struct.pack('!H', n))

    # Read the elements from the console and send them
    numbers = [int(input(f"Element {i + 1}: ")) for i in range(n)]
    client_socket.send(struct.pack('!' + 'H' * n, *numbers))

    # Receive the result
    result = struct.unpack('!H', client_socket.recv(2))[0]
    print(f"Received result: {result}")

    client_socket.close()

if __name__ == "__main__":
    main()