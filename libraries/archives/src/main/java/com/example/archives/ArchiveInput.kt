package com.example.archives

import com.github.junrar.Archive
import org.apache.commons.compress.archivers.ArchiveInputStream

class ArchiveInput {
    val input: Any
    constructor(input: ArchiveInputStream) {
        this.input = input
    }
    constructor(input: Archive) {
        this.input = input
    }
}
