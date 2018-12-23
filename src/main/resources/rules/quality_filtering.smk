if config["rules"]["removing_adapters"]:
    INPUT_DEST = "fastq_wo_adapters"
else:
    INPUT_DEST = "fastq"

rule trimmomatic_qual:
    input:
        INPUT_DEST + "/{sample}" + config["samples"]["suffix"] + "1" + config["samples"]["ext"],
        INPUT_DEST + "/{sample}" + config["samples"]["suffix"] + "2" + config["samples"]["ext"]
    output:
        "fastq_hq/{sample}" + config["samples"]["suffix"] + "1" + config["samples"]["ext"],
        "fastq_hq/{sample}" + config["samples"]["suffix"] + "1_unpaired" + config["samples"]["ext"],
        "fastq_hq/{sample}" + config["samples"]["suffix"] + "2" + config["samples"]["ext"],
        "fastq_hq/{sample}" + config["samples"]["suffix"] + "2_unpaired" + config["samples"]["ext"]
    shell:
	    "trimmomatic PE {input} {output} AVGQUAL:" + config["rules"]["min_phred_score"]
