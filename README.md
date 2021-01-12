# fastacompare
Application for comparison of biological sequences in FASTA format.

Client-side: bash shell script that uses the MASA-OpenMP framework to compare the sequences in FASTA format.
Server-side: Java WS application to collect statistical/alignment information and manage checkpoints.

Installation prerequisites:

1. Client-side:

- Linux Kernel (for bash shell script support).
- MASA-OpenMP.
- CURL.

2. Server-side

- Java 8 (preferably).
- Maven 3.X (or higher).
- Apache Tomcat 8.5.X (or higher).

The MASA-OpenMP framework is available at https://github.com/edanssandes/MASA-OpenMP.
CURL is available at https://curl.se/.

Copyright (c) 2021 - Gustavo Portella - License GPLv3
This software comes with ABSOLUTELY no warranty.
