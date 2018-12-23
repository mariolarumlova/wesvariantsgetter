rule picard_sort:
    input:
        "mapped_reads/{sample}.bam"
    output:
        bam="sorted_reads/{sample}.bam",
	    temp=directory("picard_temp/{sample}")
    shell:
        "picard SortSam "
        "VALIDATION_STRINGENCY=LENIENT "
        "INPUT={input} "
        "OUTPUT={output.bam} "
        "SORT_ORDER=coordinate "
	    "TMP_DIR={output.temp}"
