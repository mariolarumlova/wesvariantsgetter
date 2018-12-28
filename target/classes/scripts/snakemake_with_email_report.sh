#To enable running snakemake with email report run the following commands:

#installing atom
wget -O atom-amd64.deb http://atom.io/download/deb
sudo apt install gdebi-core
sudo gdebi atom-amd64.deb
#installing ssmtp
sudo apt install sharutils
sudo su
apt-get update && apt-get install ssmtp
#enter configuration file
atom /etc/ssmtp/ssmtp.conf
#Add the following commented text to an opened .conf file
#(assume you want to configure gmain account)

#root=yourlogin@gmail.com
#AuthUser=yourlogin
#AuthPass=yourpassword
#FromLineOverride=YES
#mailhub=smtp.gmail.com:587
#UseSTARTTLS=YES

#Then run the following commands
apt install mailutils
service sendmail stop
chkconfig sendmail off
mkdir /root/.bakup
mv /usr/sbin/sendmail /root/.bakup
ln -s /usr/local/ssmtp/sbin/ssmtp /usr/sbin/sendmail

#At the end copy the code above to another sh file and run via command line
# in the folder where your Snakefile is (first cd to that folder)
# whenever you want to get an e-mail after finished snakemake analysis
# (just fill with your data)

#!/bin/bash
snakemake 2> snakemake.log
echo -e "to: logintoemailyouwanttosendmessageto@domain.com\nsubject: Snakemake_finished\n"| uuenview -a -bo /home/$USER$/snakemake.log | ssmtp logintoemailyouwanttosendmessageto@domain.com