package q4.test_coverage.mytests


import org.junit.Assert.*
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null))
    }

    /** Домашнее задание: */
    @Test
    fun emailValidator_Without_Body_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(".com"))
    }

    @Test
    fun emailValidator_Without_Domain_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@gmail."))
    }

    @Test
    fun emailValidator_With_Minuses_Equals() {
        assertEquals(false, EmailValidator.isValidEmail("-----"))
    }

    @Test
    fun emailValidator_Correct_EmailNotSame() {
        assertNotSame(false, EmailValidator.isValidEmail("mail@mail.com"))
    }

    @Test
    fun emailValidator_Correct_EmailSame() {
        assertSame(true, EmailValidator.isValidEmail("mail@mail.com"))
    }

    @Test
    fun emailValidator_Null_NotNullTrue() {
        assertNotNull(null, EmailValidator.isValidEmail(""))
    }

    /** Свой метод: */
    @Test
    fun print_Words_List_isNullTrue() {
        assertNull(null, EmailValidator.printWordsArray(null))
    }

    @Test
    fun print_Words_List_isNullFalse() {
        val list = mutableListOf<String>()
        list.add("hello")
        list.add(" bro")
        list.add("!")

        assertNotNull(true.toString(), EmailValidator.printWordsArray(list))
    }

    @Test
    fun print_Words_List_NotEquals() {
        val list = mutableListOf<String>()
        list.add("hello")
        list.add(" bro")
        list.add("!")

        assertNotEquals("bro", EmailValidator.printWordsArray(list))
    }

    @Test
    fun `вывод_в_консоль_списка_СхожийТип`() {
        val list = mutableListOf<String>()
        list.add("hello")
        list.add(" bro")
        list.add("!")

        assertEquals(list.toMutableList(), EmailValidator.printWordsArray(list))
    }

}
