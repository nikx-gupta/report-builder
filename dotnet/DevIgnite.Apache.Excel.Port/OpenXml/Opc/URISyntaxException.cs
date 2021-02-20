// using System;
// using System.Text;
//
// namespace DevIgnite.Apache.Excel.Port.OpenXml.Opc {
//     public class URISyntaxException : Exception {
//         private static long serialVersionUID = 2137979680897488891L;
//
//         private String input;
//         private int index;
//
//         /**
//      * Constructs an instance from the given input string, reason, and error
//      * index.
//      *
//      * @param  input   The input string
//      * @param  reason  A string explaining why the input could not be parsed
//      * @param  index   The index at which the parse error occurred,
//      *                 or {@code -1} if the index is not known
//      *
//      * @throws  NullPointerException
//      *          If either the input or reason strings are {@code null}
//      *
//      * @throws  IllegalArgumentException
//      *          If the error index is less than {@code -1}
//      */
//         public URISyntaxException(string input, string reason, int index) : base(reason) {
//             if ((input == null) || (reason == null))
//                 throw new NullReferenceException();
//             if (index < -1)
//                 throw new ArgumentException();
//             this.input = input;
//             this.index = index;
//         }
//
//         /**
//      * Constructs an instance from the given input string and reason.  The
//      * resulting object will have an error index of {@code -1}.
//      *
//      * @param  input   The input string
//      * @param  reason  A string explaining why the input could not be parsed
//      *
//      * @throws  NullPointerException
//      *          If either the input or reason strings are {@code null}
//      */
//         public URISyntaxException(String input, String reason) : this(input, reason, -1) { }
//
//         /**
//      * Returns the input string.
//      *
//      * @return  The input string
//      */
//         public String getInput() {
//             return input;
//         }
//
//         /**
//      * Returns a string explaining why the input string could not be parsed.
//      *
//      * @return  The reason string
//      */
//         public String getReason() {
//             return base.Message;
//         }
//
//         /**
//      * Returns an index into the input string of the position at which the
//      * parse error occurred, or {@code -1} if this position is not known.
//      *
//      * @return  The error index
//      */
//         public int getIndex() {
//             return index;
//         }
//
//         /**
//      * Returns a string describing the parse error.  The resulting string
//      * consists of the reason string followed by a colon character
//      * ({@code ':'}), a space, and the input string.  If the error index is
//      * defined then the string {@code " at index "} followed by the index, in
//      * decimal, is inserted after the reason string and before the colon
//      * character.
//      *
//      * @return  A string describing the parse error
//      */
//         public String getMessage() {
//             StringBuilder sb = new StringBuilder();
//             sb.Append(getReason());
//             if (index > -1) {
//                 sb.Append(" at index ");
//                 sb.Append(index);
//             }
//
//             sb.Append(": ");
//             sb.Append(input);
//             return sb.ToString();
//         }
//     }
// }