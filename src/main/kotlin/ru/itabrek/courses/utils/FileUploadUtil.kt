package ru.itabrek.courses.utils;

class FileUploadUtil {

    companion object {
        fun getExtensionByStringHandling(filename: String): String {
            return filename.substring(filename.lastIndexOf(".") + 1)
        }
    }
}
