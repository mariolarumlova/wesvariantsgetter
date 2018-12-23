if config["rules"]["remove_duplicates"]:
    REMOVE_DUPLICATES = "true"
else:
    REMOVE_DUPLICATES = "false"

rule picard_mark_duplicates:
    input:
        "sorted_reads/{sample}.bam"
    output:
        bam="marked_reads/{sample}.bam",
	    temp=directory("picard_temp2/{sample}")
    shell:
        "picard MarkDuplicates -Xmx8g "
        "INPUT={input} "
        "OUTPUT={output.bam} "
        "METRICS_FILE={wildcards.sample}_markduplicates_metrics.txt "
        "OPTICAL_DUPLICATE_PIXEL_DISTANCE=2500 "
        "CREATE_INDEX=true "
        "TMP_DIR={output.temp} "
        "REMOVE_DUPLICATES=" + REMOVE_DUPLICATES
		" MAX_RECORDS_IN_RAM=50000"
