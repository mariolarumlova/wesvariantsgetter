rule picard_add_or_replace_read_groups:
    input:
        "marked_reads/{sample}.bam"
    output:
        "marked_reads_wg/{sample}.bam"
    shell:
        "picard AddOrReplaceReadGroups "
        "I={input} "
        "O={output} "
        "RGID=4 "
        "RGLB=lib1 "
        "RGPL=illumina "
        "RGPU=unit1 "
        "RGSM=20"

rule samtools_index:
    input:
        "marked_reads_wg/{sample}.bam"
    output:
        "marked_reads_wg/{sample}.bam.bai"
    shell:
        "samtools index {input}"

rule gatk_realigner_target_creator:
    input:
        tool=PROGRAMS_DEST + "GenomeAnalysisTK.jar",
        fa=GENOME,
        bam="marked_reads_wg/{sample}.bam",
        bai="marked_reads_wg/{sample}.bam.bai",
        fad=GENOME_REF + ".dict",
        fai=GENOME + ".fai"
    output:
        "gatk/intervals/{sample}.intervals"
    shell:
        "java -jar {input.tool} "
        "-T RealignerTargetCreator "
        "-R {input.fa} "
        "-I {input.bam} "
        "-o {output}"

rule gatk_indel_realigner:
    input:
        tool=PROGRAMS_DEST + "GenomeAnalysisTK.jar",
        fa=GENOME,
        bam="marked_reads_wg/{sample}.bam",
        intervals="gatk/intervals/{sample}.intervals"
    output:
        "realigned_reads/{sample}.bam"
    shell:
        "java -jar {input.tool} "
        "-T IndelRealigner "
        "-R {input.fa} "
        "-I {input.bam} "
        "-targetIntervals {input.intervals} "
        "-o {output}"
