package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void testCompareTo() {
        assertThat(StringUtil.compareTo(null, null)).isEqualTo(0);
        assertThat(StringUtil.compareTo("A", null)).isEqualTo(1);
        assertThat(StringUtil.compareTo(null, "B")).isEqualTo(-1);
        assertThat(StringUtil.compareTo("A", "B")).isEqualTo(-1);
        assertThat(StringUtil.compareTo("XA", "B")).isEqualTo(22);
        assertThat(StringUtil.compareTo("XA", "XA")).isEqualTo(0);
    }

    @Test
    public void testRemoveTrailingChar() {
        assertThat(StringUtil.removeTrailingChar(null, ' ')).isNull();
        assertThat(StringUtil.removeTrailingChar("Hello ", ' ')).isEqualToIgnoringCase("Hello");
        assertThat(StringUtil.removeTrailingChar("Hello     ", ' ')).isEqualToIgnoringCase("Hello");
        assertThat(StringUtil.removeTrailingChar(" Hello  ", ' ')).isEqualToIgnoringCase(" Hello");
        assertThat(StringUtil.removeTrailingChar(" Hellooo", 'o')).isEqualToIgnoringCase(" Hell");
        assertThat(StringUtil.removeTrailingChar(" Hollooo", 'o')).isEqualToIgnoringCase(" Holl");
    }

    @Test
    public void testReplaceCRToken() {
        assertThat(StringUtil.replaceCRToken(null)).isNull();
        assertThat(StringUtil.replaceCRToken("Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replaceCRToken("Hello%CR%")).isEqualTo("Hello" + System.getProperty("line.separator"));
        assertThat(StringUtil.replaceCRToken("Hel%CR%lo")).isEqualTo("Hel" + System.getProperty("line.separator") + "lo");
    }

    @Test
    public void testToUpperCase() {
        assertThat(StringUtil.toUpperCase(null)).isNull();
        assertThat(StringUtil.toUpperCase("Hi")).isEqualTo("HI");
        assertThat(StringUtil.toUpperCase("HeLLo")).isEqualTo("HELLO");
    }

    @Test
    public void testIsNotBlank() {
        assertThat(StringUtil.isNotBlank(null)).isFalse();
        assertThat(StringUtil.isNotBlank("")).isFalse();
        assertThat(StringUtil.isNotBlank(" ")).isFalse();
        assertThat(StringUtil.isNotBlank(" X")).isTrue();
        assertThat(StringUtil.isNotBlank(new Object())).isFalse();
        assertThat(StringUtil.isNotBlank(BigDecimal.ONE)).isFalse();
    }

    @Test
    public void testDefaultFormatDatetime() {
        assertThat(StringUtil.defaultFormatDatetime(new Date(1531603574189L))).isEqualToIgnoringCase("14-Jul-2018 22:26:14");
    }

    @Test
    public void testDefaultFileFormatTimestamp() {
        assertThat(StringUtil.defaultFileFormatTimestamp(new Date(1531603574189L))).isEqualToIgnoringCase("20180714-222614");
    }

    @Ignore
    public void testReplaceTokenStringStringObject() {
        fail("Not yet implemented");
    }

    @Test
    public void testReplace() {
        assertThat(StringUtil.replace(null, null, null)).isNull();
        assertThat(StringUtil.replace("hi", null, null)).isNull();
        assertThat(StringUtil.replace("hi", "ho", null)).isNull();
        assertThat(StringUtil.replace("hi", "Ho", "")).isEmpty();
        assertThat(StringUtil.replace(null, null, "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace("H", "Ti", "Hello")).isEqualTo("Tiello");
        assertThat(StringUtil.replace("WW", "Ti", "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace("l", "Ti", "Hello")).isEqualTo("HeTiTio");
        assertThat(StringUtil.replace("l", null, "Hello")).isEqualTo("Henullnullo");
        assertThat(StringUtil.replace(null, "@@", "Hello")).isEqualTo("Hello");
    }

    @Ignore
    public void testReplaceTokenStringStringString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testWrapText() {
        fail("Not yet implemented");
    }

    @Test
    public void testTrimAndUpperCase() {
        assertThat(StringUtil.trimAndUpperCase(null)).isNull();
        assertThat(StringUtil.trimAndUpperCase("")).isEmpty();
        assertThat(StringUtil.trimAndUpperCase("hi")).isEqualTo("HI");
        assertThat(StringUtil.trimAndUpperCase("  hi")).isEqualTo("HI");
        assertThat(StringUtil.trimAndUpperCase("  hi  ")).isEqualTo("HI");
        assertThat(StringUtil.trimAndUpperCase(" hi      ")).isEqualTo("HI");
    }

    @Test
    public void testToLowerCaseString() {
        assertThat(StringUtil.toLowerCase((String) null)).isNull();
        assertThat(StringUtil.toLowerCase("")).isEmpty();
        assertThat(StringUtil.toLowerCase("hi")).isEqualTo("hi");
        assertThat(StringUtil.toLowerCase("  hI")).isEqualTo("  hi");
        assertThat(StringUtil.toLowerCase("  Hi HO  ")).isEqualTo("  hi ho  ");
        assertThat(StringUtil.toLowerCase(" hi      ")).isEqualTo(" hi      ");
    }

    @Test
    public void testSingleQuote() {
        assertThat(StringUtil.singleQuote("")).isEqualTo("''");
        assertThat(StringUtil.singleQuote(null)).isEqualTo("''");
        assertThat(StringUtil.singleQuote("Hi World")).isEqualTo("'Hi World'");
        assertThat(StringUtil.singleQuote(" Hi World")).isEqualTo("' Hi World'");
    }

    @Test
    public void testNoneBlank() {
        assertThat(StringUtil.noneBlank("hi")).isTrue();
        assertThat(StringUtil.noneBlank("hi", "ho", "World")).isTrue();
        assertThat(StringUtil.noneBlank("hi", "ho", null, "World")).isFalse();
        assertThat(StringUtil.noneBlank("hi", "ho", "", "World")).isFalse();
        assertThat(StringUtil.noneBlank(null)).isFalse();
        assertThat(StringUtil.noneBlank("")).isFalse();
    }

    @Test
    public void testTrim() {
        assertThat(StringUtil.trim(null)).isNull();
        assertThat(StringUtil.trim("")).isEmpty();
        assertThat(StringUtil.trim("     ")).isEmpty();
        assertThat(StringUtil.trim("   hi  ")).isEqualTo("hi");
    }

    @Ignore
    public void testToLowerCaseStringArray() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testProcessCaseTreatment() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToStringObject() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToStringOrEmpty() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testAllEquals() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testEqualsAnyIgnoreCaseStringStringArray() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testEqualsAnyIgnoreCaseStringString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testConcat() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testConcatWithSpaces() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testEmptyIfNull() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testIsWildcardOrNull() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReturnIfNotNull() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testFirstCharToUpper() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testPrepareForNumericParsing() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testNullIfEmpty() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testAsList() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testBoxify() {
        fail("Not yet implemented");
    }

}
