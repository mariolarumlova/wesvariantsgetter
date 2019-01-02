#!/bin/bash

#1 path to miniconda, 2 environment name, 3 path to resources, 4 path to programs

# Pobranie Minicondy3
cd $1
cd ..
wget https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh -O miniconda3.sh
bash miniconda3.sh -u -p miniconda3
# Tutaj należy przejść przez instalator minicondy: zaakceptować warunki korzystania z programu (wpisując 'yes', gdy zostanie się o to poproszonym), a następnie naciskając ENTER

# Po pojawieniu się komunikatu: "Do you wish the installer to prepend the Miniconda3 install location
#to PATH in your /home/mariola/.bashrc ? [yes|no]", należy wpisać 'yes' i nacisnąć ENTER

# Gdyby nie został wyświetlony komunikat o dodaniu narzędzia conda do pliku ~/.bashrc, to należy wywołać następujące komendy
export PATH="$1bin:$PATH"
echo ". $1etc/profile.d/conda.sh" >> ~/.bashrc

# Dalsza część konfiguracji musi zostać przeprowadzona w nowym terminalu. W tym celu należy skorzystać ze skrótu klawiszowego Ctrl + Alt + T, a następnie wprowadzić następujące komendy:
conda install -n root _license
hash -r
conda config --set always_yes yes --set changeps1 no
conda update -q conda
conda config --add channels defaults
conda config --add channels conda-forge
conda config --add channels bioconda
conda create -q -n snakemake snakemake>=5.1.2 python=3.6
source activate snakemake
conda env create --name $2 --file environment.yaml
conda activate $2

# Pobranie programów nieuwzględnionych w Condzie
cd $4

# Pobranie SNVSniffer
wget https://sourceforge.net/projects/snvsniffer/files/latest/download/SNVSniffer-v2.0.4_bin_x86_64.tar.gz
tar xvzf SNVSniffer-v2.0.4_bin_x86_64.tar.gz

# Pobranie FaSD-somatic 
wget http://147.8.193.36:8080/hkubioinfo/FaSD-somatic/FaSD-somatic.zip
unzip FaSD-somatic.zip

# GenomeAnalysisToolKit należy pobrać ręcznie z poniższej strony, a po rozpakowaniu przenieść plik
# GenomeAnalysisTK.jar do folderu $4
https://software.broadinstitute.org/gatk/download/auth?package=GATK-archive&version=3.8-1-0-gf15c1c3ef

# W celu użycia opcji 'anotacja wariantów' należy pobrać narzędzie ANNOVAR wg instrukcji z poniższej witryny:
http://annovar.openbioinformatics.org/en/latest/user-guide/download/