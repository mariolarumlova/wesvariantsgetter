rule picard_create_sequence_dictionary:
    input:
        GENOME
    output:
        GENOME_REF + ".dict"
    shell:
        "picard CreateSequenceDictionary "
        "R={input} "
        "O={output}"

rule samtools_index_genome:
    input:
        GENOME
    output:
        GENOME + ".fai"
    shell:
        "samtools faidx {input}"
