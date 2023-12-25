#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

void server_client(int c) {
        uint16_t length, length2;
        char buffer[100], buffer2[100];

        memset(buffer, 0, 100);
        memset(buffer2, 0, 100);

        // Get the first string
        recv(c, &length, sizeof(length), 0);
        length = ntohs(length);
        recv(c, buffer, length * sizeof(char), 0);

        // Get the secons string
        recv(c, &length2, sizeof(length2), 0);
        length2 = ntohs(length2);
        recv(c, buffer2, length2 * sizeof(char), 0);

        // Ckeck if they have the same length
        if (length2 != length) {
                printf("The strings have different sizes\n");
                exit(1);
        }

	// Get the frequency of all characters by their ASCII code
        int ascii[260]={0}, maxIndex=0;
        for (int i = 0; i < length - 1; i++) {
                if (buffer[i] == buffer2[i]) {
                        ascii[(int)buffer[i]]++;
                }
        }

        // Get the most frequent ASCII code
        for (int i = 0; i <= 255; i++) {
                if (ascii[i] > ascii[maxIndex]) {
                        maxIndex = i;
                }
        }

        // Send the most frequently used character and it's frequency
        char character = (char)maxIndex;
        send(c, &character, sizeof(char), 0);
        uint16_t freq = htons(ascii[maxIndex]);
        send(c, &freq, sizeof(freq), 0);


        close(c);
        exit(0);
}

int main() {
        int s;
        struct sockaddr_in server, client;
        int c, l;
        s = socket(AF_INET, SOCK_STREAM, 0);
        if (s < 0) {
                printf("Socket creation failed - server");
                exit(1);
        }

        memset(&server, 0, sizeof(server));
        server.sin_port = htons(1234);
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = INADDR_ANY;

        if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
                printf("Bind error");
                exit(1);
        }

        listen(s, 5);

        l = sizeof(client);
        memset(&client, 0, sizeof(client));

        while (1) {
                c = accept(s, (struct sockaddr *) &client, &l);
                printf("Client connected\n");
                if (fork() == 0) {
                        server_client(c);
                        return 0;
                }
        }

}