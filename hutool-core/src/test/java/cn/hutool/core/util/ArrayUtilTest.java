package cn.hutool.core.util;

import cn.hutool.core.collection.ListUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * {@link ArrayUtil} 数组工具单元测试
 *
 * @author Looly
 */
public class ArrayUtilTest {

	@Test
	public void isEmptyTest() {
		final int[] a = {};
		Assert.assertTrue(ArrayUtil.isEmpty(a));
		Assert.assertTrue(ArrayUtil.isEmpty((Object) a));
		final int[] b = null;
		//noinspection ConstantConditions
		Assert.assertTrue(ArrayUtil.isEmpty(b));
		final Object c = null;
		//noinspection ConstantConditions
		Assert.assertTrue(ArrayUtil.isEmpty(c));

		Object d = new Object[]{"1", "2", 3, 4D};
		boolean isEmpty = ArrayUtil.isEmpty(d);
		Assert.assertFalse(isEmpty);
		d = new Object[0];
		isEmpty = ArrayUtil.isEmpty(d);
		Assert.assertTrue(isEmpty);
		d = null;
		//noinspection ConstantConditions
		isEmpty = ArrayUtil.isEmpty(d);
		//noinspection ConstantConditions
		Assert.assertTrue(isEmpty);

		// Object数组
		final Object[] e = new Object[]{"1", "2", 3, 4D};
		final boolean empty = ArrayUtil.isEmpty(e);
		Assert.assertFalse(empty);
	}

	@Test
	public void isNotEmptyTest() {
		final int[] a = {1, 2};
		Assert.assertTrue(ArrayUtil.isNotEmpty(a));

		final String[] b = {"a", "b", "c"};
		Assert.assertTrue(ArrayUtil.isNotEmpty(b));

		final Object c = new Object[]{"1", "2", 3, 4D};
		Assert.assertTrue(ArrayUtil.isNotEmpty(c));
	}

	@Test
	public void newArrayTest() {
		final String[] newArray = ArrayUtil.newArray(String.class, 3);
		Assert.assertEquals(3, newArray.length);

		final Object[] newArray2 = ArrayUtil.newArray(3);
		Assert.assertEquals(3, newArray2.length);
	}

	@Test
	public void cloneTest() {
		final Integer[] b = {1, 2, 3};
		final Integer[] cloneB = ArrayUtil.clone(b);
		Assert.assertArrayEquals(b, cloneB);

		final int[] a = {1, 2, 3};
		final int[] clone = ArrayUtil.clone(a);
		Assert.assertArrayEquals(a, clone);
	}

	@Test
	public void filterEditTest() {
		final Integer[] a = {1, 2, 3, 4, 5, 6};
		final Integer[] filter = ArrayUtil.edit(a, t -> (t % 2 == 0) ? t : null);
		Assert.assertArrayEquals(filter, new Integer[]{2, 4, 6});
	}

	@Test
	public void filterTestForFilter() {
		final Integer[] a = {1, 2, 3, 4, 5, 6};
		final Integer[] filter = ArrayUtil.filter(a, t -> t % 2 == 0);
		Assert.assertArrayEquals(filter, new Integer[]{2, 4, 6});
	}

	@Test
	public void editTest() {
		final Integer[] a = {1, 2, 3, 4, 5, 6};
		final Integer[] filter = ArrayUtil.edit(a, t -> (t % 2 == 0) ? t * 10 : t);
		Assert.assertArrayEquals(filter, new Integer[]{1, 20, 3, 40, 5, 60});
	}

	@Test
	public void indexOfTest() {
		final Integer[] a = {1, 2, 3, 4, 5, 6};
		final int index = ArrayUtil.indexOf(a, 3);
		Assert.assertEquals(2, index);

		final long[] b = {1, 2, 3, 4, 5, 6};
		final int index2 = ArrayUtil.indexOf(b, 3);
		Assert.assertEquals(2, index2);
	}

	@Test
	public void lastIndexOfTest() {
		final Integer[] a = {1, 2, 3, 4, 3, 6};
		final int index = ArrayUtil.lastIndexOf(a, 3);
		Assert.assertEquals(4, index);

		final long[] b = {1, 2, 3, 4, 3, 6};
		final int index2 = ArrayUtil.lastIndexOf(b, 3);
		Assert.assertEquals(4, index2);
	}

	@Test
	public void containsTest() {
		final Integer[] a = {1, 2, 3, 4, 3, 6};
		final boolean contains = ArrayUtil.contains(a, 3);
		Assert.assertTrue(contains);

		final long[] b = {1, 2, 3, 4, 3, 6};
		final boolean contains2 = ArrayUtil.contains(b, 3);
		Assert.assertTrue(contains2);
	}

	@Test
	public void containsAnyTest() {
		final Integer[] a = {1, 2, 3, 4, 3, 6};
		boolean contains = ArrayUtil.containsAny(a, 4, 10, 40);
		Assert.assertTrue(contains);

		contains = ArrayUtil.containsAny(a, 10, 40);
		Assert.assertFalse(contains);
	}

	@Test
	public void containsAllTest() {
		final Integer[] a = {1, 2, 3, 4, 3, 6};
		boolean contains = ArrayUtil.containsAll(a, 4, 2, 6);
		Assert.assertTrue(contains);

		contains = ArrayUtil.containsAll(a, 1, 2, 3, 5);
		Assert.assertFalse(contains);
	}

	@Test
	public void mapTest() {
		final String[] keys = {"a", "b", "c"};
		final Integer[] values = {1, 2, 3};
		final Map<String, Integer> map = ArrayUtil.zip(keys, values, true);
		Assert.assertEquals(Objects.requireNonNull(map).toString(), "{a=1, b=2, c=3}");
	}

	@Test
	public void castTest() {
		final Object[] values = {"1", "2", "3"};
		final String[] cast = (String[]) ArrayUtil.cast(String.class, values);
		Assert.assertEquals(values[0], cast[0]);
		Assert.assertEquals(values[1], cast[1]);
		Assert.assertEquals(values[2], cast[2]);
	}

	@Test
	public void maxTest() {
		final int max = ArrayUtil.max(1, 2, 13, 4, 5);
		Assert.assertEquals(13, max);

		final long maxLong = ArrayUtil.max(1L, 2L, 13L, 4L, 5L);
		Assert.assertEquals(13, maxLong);

		final double maxDouble = ArrayUtil.max(1D, 2.4D, 13.0D, 4.55D, 5D);
		Assert.assertEquals(13.0, maxDouble, 0);

		final BigDecimal one = new BigDecimal("1.00");
		final BigDecimal two = new BigDecimal("2.0");
		final BigDecimal three = new BigDecimal("3");
		final BigDecimal[] bigDecimals = {two, one, three};

		final BigDecimal minAccuracy = ArrayUtil.min(bigDecimals, Comparator.comparingInt(BigDecimal::scale));
		Assert.assertEquals(minAccuracy, three);

		final BigDecimal maxAccuracy = ArrayUtil.max(bigDecimals, Comparator.comparingInt(BigDecimal::scale));
		Assert.assertEquals(maxAccuracy, one);
	}

	@Test
	public void minTest() {
		final int min = ArrayUtil.min(1, 2, 13, 4, 5);
		Assert.assertEquals(1, min);

		final long minLong = ArrayUtil.min(1L, 2L, 13L, 4L, 5L);
		Assert.assertEquals(1, minLong);

		final double minDouble = ArrayUtil.min(1D, 2.4D, 13.0D, 4.55D, 5D);
		Assert.assertEquals(1.0, minDouble, 0);
	}

	@Test
	public void appendTest() {
		final String[] a = {"1", "2", "3", "4"};
		final String[] b = {"a", "b", "c"};

		final String[] result = ArrayUtil.append(a, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);
	}

	@Test
	public void insertTest() {
		final String[] a = {"1", "2", "3", "4"};
		final String[] b = {"a", "b", "c"};

		// 在-1位置插入，相当于在3位置插入
		String[] result = ArrayUtil.insert(a, -1, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "a", "b", "c", "4"}, result);

		// 在第0个位置插入，即在数组前追加
		result = ArrayUtil.insert(a, 0, b);
		Assert.assertArrayEquals(new String[]{"a", "b", "c", "1", "2", "3", "4"}, result);

		// 在第2个位置插入，即"3"之前
		result = ArrayUtil.insert(a, 2, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "a", "b", "c", "3", "4"}, result);

		// 在第4个位置插入，即"4"之后，相当于追加
		result = ArrayUtil.insert(a, 4, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);

		// 在第5个位置插入，由于数组长度为4，因此补null
		result = ArrayUtil.insert(a, 5, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "4", null, "a", "b", "c"}, result);
	}

	@Test
	public void joinTest() {
		final String[] array = {"aa", "bb", "cc", "dd"};
		final String join = ArrayUtil.join(array, ",", "[", "]");
		Assert.assertEquals("[aa],[bb],[cc],[dd]", join);

		final Object array2 = new String[]{"aa", "bb", "cc", "dd"};
		final String join2 = ArrayUtil.join(array2, ",");
		Assert.assertEquals("aa,bb,cc,dd", join2);
	}

	@Test
	public void getArrayTypeTest() {
		Class<?> arrayType = ArrayUtil.getArrayType(int.class);
		Assert.assertSame(int[].class, arrayType);

		arrayType = ArrayUtil.getArrayType(String.class);
		Assert.assertSame(String[].class, arrayType);
	}

	@Test
	public void distinctTest() {
		final String[] array = {"aa", "bb", "cc", "dd", "bb", "dd"};
		final String[] distinct = ArrayUtil.distinct(array);
		Assert.assertArrayEquals(new String[]{"aa", "bb", "cc", "dd"}, distinct);
	}

	@Test
	public void distinctByFunctionTest() {
		final String[] array = {"aa", "Aa", "BB", "bb"};

		// 覆盖模式下，保留最后加入的两个元素
		String[] distinct = ArrayUtil.distinct(array, String::toLowerCase, true);
		Assert.assertArrayEquals(new String[]{"Aa", "bb"}, distinct);

		// 忽略模式下，保留最早加入的两个元素
		distinct = ArrayUtil.distinct(array, String::toLowerCase, false);
		Assert.assertArrayEquals(new String[]{"aa", "BB"}, distinct);
	}

	@Test
	public void toStingTest() {
		final int[] a = {1, 3, 56, 6, 7};
		Assert.assertEquals("[1, 3, 56, 6, 7]", ArrayUtil.toString(a));
		final long[] b = {1, 3, 56, 6, 7};
		Assert.assertEquals("[1, 3, 56, 6, 7]", ArrayUtil.toString(b));
		final short[] c = {1, 3, 56, 6, 7};
		Assert.assertEquals("[1, 3, 56, 6, 7]", ArrayUtil.toString(c));
		final double[] d = {1, 3, 56, 6, 7};
		Assert.assertEquals("[1.0, 3.0, 56.0, 6.0, 7.0]", ArrayUtil.toString(d));
		final byte[] e = {1, 3, 56, 6, 7};
		Assert.assertEquals("[1, 3, 56, 6, 7]", ArrayUtil.toString(e));
		final boolean[] f = {true, false, true, true, true};
		Assert.assertEquals("[true, false, true, true, true]", ArrayUtil.toString(f));
		final float[] g = {1, 3, 56, 6, 7};
		Assert.assertEquals("[1.0, 3.0, 56.0, 6.0, 7.0]", ArrayUtil.toString(g));
		final char[] h = {'a', 'b', '你', '好', '1'};
		Assert.assertEquals("[a, b, 你, 好, 1]", ArrayUtil.toString(h));

		final String[] array = {"aa", "bb", "cc", "dd", "bb", "dd"};
		Assert.assertEquals("[aa, bb, cc, dd, bb, dd]", ArrayUtil.toString(array));
	}

	@Test
	public void toArrayTest() {
		final ArrayList<String> list = ListUtil.of("A", "B", "C", "D");
		final String[] array = ArrayUtil.toArray(list, String.class);
		Assert.assertEquals("A", array[0]);
		Assert.assertEquals("B", array[1]);
		Assert.assertEquals("C", array[2]);
		Assert.assertEquals("D", array[3]);
	}

	@Test
	public void addAllTest() {
		final int[] ints = ArrayUtil.addAll(new int[]{1, 2, 3}, new int[]{4, 5, 6});
		Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, ints);
	}

	@Test
	public void isAllNotNullTest() {
		final String[] allNotNull = {"aa", "bb", "cc", "dd", "bb", "dd"};
		Assert.assertTrue(ArrayUtil.isAllNotNull(allNotNull));
		final String[] hasNull = {"aa", "bb", "cc", null, "bb", "dd"};
		Assert.assertFalse(ArrayUtil.isAllNotNull(hasNull));
	}

	@Test
	public void indexOfSubTest() {
		final Integer[] a = {0x12, 0x34, 0x56, 0x78, 0x9A};
		final Integer[] b = {0x56, 0x78};
		final Integer[] c = {0x12, 0x56};
		final Integer[] d = {0x78, 0x9A};
		final Integer[] e = {0x78, 0x9A, 0x10};

		int i = ArrayUtil.indexOfSub(a, b);
		Assert.assertEquals(2, i);

		i = ArrayUtil.indexOfSub(a, c);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.indexOfSub(a, d);
		Assert.assertEquals(3, i);

		i = ArrayUtil.indexOfSub(a, e);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.indexOfSub(a, null);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.indexOfSub(null, null);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.indexOfSub(null, b);
		Assert.assertEquals(-1, i);
	}

	@Test
	public void indexOfSubTest2() {
		final Integer[] a = {0x12, 0x56, 0x34, 0x56, 0x78, 0x9A};
		final Integer[] b = {0x56, 0x78};
		final int i = ArrayUtil.indexOfSub(a, b);
		Assert.assertEquals(3, i);
	}

	@Test
	public void lastIndexOfSubTest() {
		final Integer[] a = {0x12, 0x34, 0x56, 0x78, 0x9A};
		final Integer[] b = {0x56, 0x78};
		final Integer[] c = {0x12, 0x56};
		final Integer[] d = {0x78, 0x9A};
		final Integer[] e = {0x78, 0x9A, 0x10};

		int i = ArrayUtil.lastIndexOfSub(a, b);
		Assert.assertEquals(2, i);

		i = ArrayUtil.lastIndexOfSub(a, c);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.lastIndexOfSub(a, d);
		Assert.assertEquals(3, i);

		i = ArrayUtil.lastIndexOfSub(a, e);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.lastIndexOfSub(a, null);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.lastIndexOfSub(null, null);
		Assert.assertEquals(-1, i);

		i = ArrayUtil.lastIndexOfSub(null, b);
		Assert.assertEquals(-1, i);
	}

	@Test
	public void lastIndexOfSubTest2() {
		final Integer[] a = {0x12, 0x56, 0x78, 0x56, 0x21, 0x9A};
		final Integer[] b = {0x56, 0x78};
		final int i = ArrayUtil.indexOfSub(a, b);
		Assert.assertEquals(1, i);
	}

	@Test
	public void reverseTest() {
		final int[] a = {1, 2, 3, 4};
		final int[] reverse = ArrayUtil.reverse(a);
		Assert.assertArrayEquals(new int[]{4, 3, 2, 1}, reverse);
	}

	@Test
	public void reverseTest2s() {
		final Object[] a = {"1", '2', "3", 4};
		final Object[] reverse = ArrayUtil.reverse(a);
		Assert.assertArrayEquals(new Object[]{4, "3", '2', "1"}, reverse);
	}

	@Test
	public void removeEmptyTest() {
		final String[] a = {"a", "b", "", null, " ", "c"};
		final String[] resultA = {"a", "b", " ", "c"};
		Assert.assertArrayEquals(ArrayUtil.removeEmpty(a), resultA);
	}

	@Test
	public void removeBlankTest() {
		final String[] a = {"a", "b", "", null, " ", "c"};
		final String[] resultA = {"a", "b", "c"};
		Assert.assertArrayEquals(ArrayUtil.removeBlank(a), resultA);
	}

	@Test
	public void nullToEmptyTest() {
		final String[] a = {"a", "b", "", null, " ", "c"};
		final String[] resultA = {"a", "b", "", "", " ", "c"};
		Assert.assertArrayEquals(ArrayUtil.nullToEmpty(a), resultA);
	}

	@Test
	public void wrapTest() {
		final Object a = new int[]{1, 2, 3, 4};
		final Object[] wrapA = ArrayUtil.wrap(a);
		for (final Object o : wrapA) {
			Assert.assertTrue(o instanceof Integer);
		}
	}

	@Test
	public void splitTest() {
		final byte[] array = new byte[1024];
		final byte[][] arrayAfterSplit = ArrayUtil.split(array, 500);
		Assert.assertEquals(3, arrayAfterSplit.length);
		Assert.assertEquals(24, arrayAfterSplit[2].length);

		final byte[] arr = {1, 2, 3, 4, 5, 6, 7};
		Assert.assertArrayEquals(new byte[][]{{1, 2, 3, 4, 5, 6, 7}}, ArrayUtil.split(arr, 8));
		Assert.assertArrayEquals(new byte[][]{{1, 2, 3, 4, 5, 6, 7}}, ArrayUtil.split(arr, 7));
		Assert.assertArrayEquals(new byte[][]{{1, 2, 3, 4}, {5, 6, 7}}, ArrayUtil.split(arr, 4));
		Assert.assertArrayEquals(new byte[][]{{1, 2, 3}, {4, 5, 6}, {7}}, ArrayUtil.split(arr, 3));
		Assert.assertArrayEquals(new byte[][]{{1, 2}, {3, 4}, {5, 6}, {7}}, ArrayUtil.split(arr, 2));
		Assert.assertArrayEquals(new byte[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}}, ArrayUtil.split(arr, 1));
	}

	@Test
	public void getTest() {
		final String[] a = {"a", "b", "c"};
		final Object o = ArrayUtil.get(a, -1);
		Assert.assertEquals("c", o);
	}

	@Test
	public void replaceTest() {
		final String[] a = {"1", "2", "3", "4"};
		final String[] b = {"a", "b", "c"};

		// 在小于0的位置，-1位置插入，返回b+a，新数组
		String[] result = ArrayUtil.replace(a, -1, b);
		Assert.assertArrayEquals(new String[]{"a", "b", "c", "1", "2", "3", "4"}, result);

		// 在第0个位置开始替换，返回a
		result = ArrayUtil.replace(ArrayUtil.clone(a), 0, b);
		Assert.assertArrayEquals(new String[]{"a", "b", "c", "4"}, result);

		// 在第1个位置替换，即"2"开始
		result = ArrayUtil.replace(ArrayUtil.clone(a), 1, b);
		Assert.assertArrayEquals(new String[]{"1", "a", "b", "c"}, result);

		// 在第2个位置插入，即"3"之后
		result = ArrayUtil.replace(ArrayUtil.clone(a), 2, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "a", "b", "c"}, result);

		// 在第3个位置插入，即"4"之后
		result = ArrayUtil.replace(ArrayUtil.clone(a), 3, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "a", "b", "c"}, result);

		// 在第4个位置插入，数组长度为4，在索引4出替换即两个数组相加
		result = ArrayUtil.replace(ArrayUtil.clone(a), 4, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);

		// 在大于3个位置插入，数组长度为4，即两个数组相加
		result = ArrayUtil.replace(ArrayUtil.clone(a), 5, b);
		Assert.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);

		final String[] e = null;
		final String[] f = {"a", "b", "c"};

		// e为null 返回 f
		result = ArrayUtil.replace(e, -1, f);
		Assert.assertArrayEquals(f, result);

		final String[] g = {"a", "b", "c"};
		final String[] h = null;

		// h为null 返回 g
		result = ArrayUtil.replace(g, 0, h);
		Assert.assertArrayEquals(g, result);
	}

	@Test
	public void setOrAppendTest() {
		final String[] arr = new String[0];
		final String[] newArr = ArrayUtil.setOrAppend(arr, 0, "Good");// ClassCastException
		Assert.assertArrayEquals(new String[]{"Good"}, newArr);

		// 非空数组替换第一个元素
		int[] arr2 = new int[]{1};
		int[] o = ArrayUtil.setOrAppend(arr2, 0, 2);
		Assert.assertArrayEquals(new int[]{2}, o);

		// 空数组追加
		arr2 = new int[0];
		o = ArrayUtil.setOrAppend(arr2, 0, 2);
		Assert.assertArrayEquals(new int[]{2}, o);
	}

	@Test
	public void getAnyTest() {
		final String[] a = {"a", "b", "c", "d", "e"};
		final Object o = ArrayUtil.getAny(a, 3, 4);
		final String[] resultO = (String[]) o;
		final String[] c = {"d", "e"};
		Assert.assertTrue(ArrayUtil.containsAll(c, resultO[0], resultO[1]));
	}

	@Test
	public void hasNonNullTest() {
		String[] a = {null, "e"};
		Assert.assertTrue(ArrayUtil.hasNonNull(a));

		a = new String[]{null, null};
		Assert.assertFalse(ArrayUtil.hasNonNull(a));

		a = new String[]{"", null};
		Assert.assertTrue(ArrayUtil.hasNonNull(a));

		a = new String[]{null};
		Assert.assertFalse(ArrayUtil.hasNonNull(a));

		a = new String[]{};
		Assert.assertFalse(ArrayUtil.hasNonNull(a));

		a = null;
		Assert.assertFalse(ArrayUtil.hasNonNull(a));
	}

	@Test
	public void isAllNullTest() {
		String[] a = {null, "e"};
		Assert.assertFalse(ArrayUtil.isAllNull(a));

		a = new String[]{null, null};
		Assert.assertTrue(ArrayUtil.isAllNull(a));

		a = new String[]{"", null};
		Assert.assertFalse(ArrayUtil.isAllNull(a));

		a = new String[]{null};
		Assert.assertTrue(ArrayUtil.isAllNull(a));

		a = new String[]{};
		Assert.assertTrue(ArrayUtil.isAllNull(a));

		a = null;
		Assert.assertTrue(ArrayUtil.isAllNull(a));
	}

	@Test
	public void insertPrimitiveTest() {
		// https://gitee.com/dromara/hutool/pulls/874

		final boolean[] booleans = new boolean[10];
		final byte[] bytes = new byte[10];
		final char[] chars = new char[10];
		final short[] shorts = new short[10];
		final int[] ints = new int[10];
		final long[] longs = new long[10];
		final float[] floats = new float[10];
		final double[] doubles = new double[10];

		final boolean[] insert1 = ArrayUtil.insert(booleans, 0, 0, 1, 2);
		Assert.assertNotNull(insert1);
		final byte[] insert2 = ArrayUtil.insert(bytes, 0, 1, 2, 3);
		Assert.assertNotNull(insert2);
		final char[] insert3 = ArrayUtil.insert(chars, 0, 1, 2, 3);
		Assert.assertNotNull(insert3);
		final short[] insert4 = ArrayUtil.insert(shorts, 0, 1, 2, 3);
		Assert.assertNotNull(insert4);
		final int[] insert5 = ArrayUtil.insert(ints, 0, 1, 2, 3);
		Assert.assertNotNull(insert5);
		final long[] insert6 = ArrayUtil.insert(longs, 0, 1, 2, 3);
		Assert.assertNotNull(insert6);
		final float[] insert7 = ArrayUtil.insert(floats, 0, 1, 2, 3);
		Assert.assertNotNull(insert7);
		final double[] insert8 = ArrayUtil.insert(doubles, 0, 1, 2, 3);
		Assert.assertNotNull(insert8);
	}

	@Test
	public void subTest() {
		final int[] arr = {1, 2, 3, 4, 5};
		int[] empty = new int[0];
		Assert.assertArrayEquals(empty, ArrayUtil.sub(arr, 2, 2));
		Assert.assertArrayEquals(empty, ArrayUtil.sub(arr, 5, 5));
		Assert.assertArrayEquals(empty, ArrayUtil.sub(arr, 5, 7));
		Assert.assertArrayEquals(arr, ArrayUtil.sub(arr, 0, 5));
		Assert.assertArrayEquals(arr, ArrayUtil.sub(arr, 5, 0));
		Assert.assertArrayEquals(arr, ArrayUtil.sub(arr, 0, 7));
		Assert.assertArrayEquals(new int[]{1}, ArrayUtil.sub(arr, 0, 1));
		Assert.assertArrayEquals(new int[]{5}, ArrayUtil.sub(arr, 4, 5));
		Assert.assertArrayEquals(new int[]{2, 3, 4}, ArrayUtil.sub(arr, 1, 4));
		Assert.assertArrayEquals(new int[]{2, 3, 4}, ArrayUtil.sub(arr, 4, 1));
		Assert.assertArrayEquals(new int[]{2, 3, 4}, ArrayUtil.sub(arr, 1, -1));
		Assert.assertArrayEquals(new int[]{2, 3, 4}, ArrayUtil.sub(arr, -1, 1));
		Assert.assertArrayEquals(new int[]{2, 3, 4}, ArrayUtil.sub(arr, -1, 1));
		Assert.assertArrayEquals(new int[]{2, 3, 4}, ArrayUtil.sub(arr, -4, -1));
	}

	@Test
	public void isSortedTest() {
		final Integer[] a = {1, 1, 2, 2, 2, 3, 3};
		Assert.assertTrue(ArrayUtil.isSorted(a));
		Assert.assertTrue(ArrayUtil.isSorted(a, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(a, null));

		final Integer[] b = {1, 1, 1, 1, 1, 1};
		Assert.assertTrue(ArrayUtil.isSorted(b));
		Assert.assertTrue(ArrayUtil.isSorted(b, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(a, null));

		final Integer[] c = {3, 3, 2, 2, 2, 1, 1};
		Assert.assertTrue(ArrayUtil.isSorted(c));
		Assert.assertTrue(ArrayUtil.isSorted(c, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(a, null));

		Assert.assertFalse(ArrayUtil.isSorted(null));
		Assert.assertFalse(ArrayUtil.isSorted(null, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(null, null));

		final Integer[] d = {};
		Assert.assertFalse(ArrayUtil.isSorted(d));
		Assert.assertFalse(ArrayUtil.isSorted(d, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(d, null));

		final Integer[] e = {1};
		Assert.assertTrue(ArrayUtil.isSorted(e));
		Assert.assertTrue(ArrayUtil.isSorted(e, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(e, null));

		final Integer[] f = {1, 2};
		Assert.assertTrue(ArrayUtil.isSorted(f));
		Assert.assertTrue(ArrayUtil.isSorted(f, Integer::compareTo));
		Assert.assertFalse(ArrayUtil.isSorted(f, null));
	}

	@Test
	public void hasSameElementTest() {
		final Integer[] a = {1, 1};
		Assert.assertTrue(ArrayUtil.hasSameElement(a));

		final String[] b = {"a", "b", "c"};
		Assert.assertFalse(ArrayUtil.hasSameElement(b));

		final Object[] c = new Object[]{"1", "2", 2, 4D};
		Assert.assertFalse(ArrayUtil.hasSameElement(c));

		final Object[] d = new Object[]{"1", "2", "2", 4D};
		Assert.assertTrue(ArrayUtil.hasSameElement(d));

		final Object[] e = new Object[]{"1", 2, 2, 4D};
		Assert.assertTrue(ArrayUtil.hasSameElement(e));

	}
}
