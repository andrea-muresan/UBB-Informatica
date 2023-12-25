import socket
import threading
import struct

def socket_thread(c):
    # Receive the length of the array
    length = struct.unpack('!H', c.recv(2))[0]

    # Receive the array elements
    numbers = struct.unpack('!' + 'H' * length, c.recv(length * 2))

    # Perform the computation
    sum_result = sum(numbers)

    # Send the result back to the client
    c.send(struct.pack('!H', sum_result))

    # Close the connection
    c.close()

def main():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1235)

    s.bind(server_address)
    s.listen(5)

    print("Server listening on {}:{}".format(*server_address))

    threads = []

    while True:
        c, addr = s.accept()
        print("Client connected from {}".format(addr))

        # Start a new thread to handle the client
        thread = threading.Thread(target=socket_thread, args=(c,))
        thread.start()

        threads.append(thread)

        # Remove completed threads
        threads = [thread for thread in threads if thread.is_alive()]

if __name__ == "__main__":
    main()