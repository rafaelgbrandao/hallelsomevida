package com.hallel.domain.utils

/*###############   Regex   ###############*/
const val PHONE_CLEAR_REGEX = "[\\s/)(.-]"
const val DATA_CLEAR_REGEX = "[\\s\\/]"
const val EMAIL_REGEX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9-](?!.*?\\.\\.)[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"

/*###############   Masks   ###############*/
const val PHONE_MASK = "{([00]) [00000]-[0000]"
const val DATE_MASK = "[00]{/}[00]{/}[9900]"

/*###############   Pattens   ###############*/
const val DATE_PATTERN_LITTLE_ENDIAN = "dd/MM/yyyy"