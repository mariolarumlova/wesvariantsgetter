if config["rules"]["removing_adapters"]:
    INPUT_DEST = "fastq_wo_adapters"
else:
    INPUT_DEST = config["samples"]["path"]

rule trimmomatic_qual:
    input:
        INPUT_DEST + "/{sample}1" + config["samples"]["ext"],
        INPUT_DEST + "/{sample}2" + config["samples"]["ext"]
    output:
        "fastq_hq/{sample}1" + config["samples"]["ext"],
        "fastq_hq/{sample}1_unpaired" + config["samples"]["ext"],
        "fastq_hq/{sample}2" + config["samples"]["ext"],
        "fastq_hq/{sample}2_unpaired" + config["samples"]["ext"]
    shell:
	    "trimmomatic PE {input} {output} AVGQUAL:" + str(config["rules"]["min_phred_score"])
