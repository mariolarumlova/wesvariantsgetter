#!/bin/bash
snakemake 2> snakemake.log
echo -e "to: mariolarumlova@gmail.com\nsubject: Snakemake_finished\n"| uuenview -a -bo /home/mariola/snakemake.log | ssmtp mariolarumlova@gmail.com
