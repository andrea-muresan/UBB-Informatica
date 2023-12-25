#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

int main() {
        int c;
        struct sockaddr_in server;
        char buffer[100], buffer2[100], character;
        uint16_t length, length2, frequency;

        c = socket(AF_INET, SOCK_STREAM, 0);
        if (c < 0) {
                printf("Socket creation failed - client\n");
                exit(1);
        }

        memset(&server, 0, sizeof(server));
        server.sin_port = htons(1234);
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = inet_addr("127.0.0.1");

        if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
                printf("Connection failed\n");
                exit(1);
        }

        memset(buffer, 0, 100);
        memset(buffer2, 0, 100);

 	// Send the first string with it's length
        printf("String1: ");
        fgets(buffer, 100, stdin);
        length = htons(strlen(buffer));
        send(c, &length, sizeof(length), 0);
        send(c, buffer, ntohs(length) * sizeof(char), 0);

        // Send the second string with it's length
        printf("String2: ");
        fgets(buffer2, 100, stdin);
        length2 = htons(strlen(buffer2));
        send(c, &length2, sizeof(length2), 0);
        send(c, buffer2, ntohs(length2) * sizeof(char), 0);

        // Get the most frequently used character and its frequency
        recv(c, &character, sizeof(char), 0);
        recv(c, &frequency, sizeof(frequency), 0);
        frequency = ntohs(frequency);
        printf("Most common character: %c, frequency %d\n", character, frequency);

        close(c);
}