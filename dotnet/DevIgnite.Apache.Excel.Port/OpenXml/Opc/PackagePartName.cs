// using System;
// using System.Globalization;
// using System.Text.RegularExpressions;
// using DevIgnite.Apache.Excel.Port.OpenXml.Opc;
//
// namespace DevIgnite.Apache.Excel.Port.OpenXml.Opc {
//     public class PackagePartName : IComparable<PackagePartName> {
//         /**
// 	 * Part name stored as an URI.
// 	 */
//         private Uri partNameURI;
//
//         /*
//          * URI Characters definition (RFC 3986)
//          */
//
//         /**
// 	 * Reserved characters for sub delimitations.
// 	 */
//         private static String[] RFC3986_PCHAR_SUB_DELIMS = {
//             "!", "$", "&", "'",
//             "(", ")", "*", "+", ",", ";", "="
//         };
//
//         /**
// 	 * Unreserved character (+ ALPHA & DIGIT).
// 	 */
//         private static String[] RFC3986_PCHAR_UNRESERVED_SUP = { "-", ".", "_", "~" };
//
//         /**
// 	 * Authorized reserved characters for pChar.
// 	 */
//         private static String[] RFC3986_PCHAR_AUTHORIZED_SUP = { ":", "@" };
//
//         /**
// 	 * Flag to know if this part name is from a relationship part name.
// 	 */
//         private bool isRelationship;
//
//         /**
// 	 * Constructor. Makes a ValidPartName object from a java.net.URI
// 	 *
// 	 * @param uri
// 	 *            The URI to validate and to transform into ValidPartName.
// 	 * @param checkConformance
// 	 *            Flag to specify if the contructor have to validate the OPC
// 	 *            conformance. Must be always <code>true</code> except for
// 	 *            special URI like '/' which is needed for internal use by
// 	 *            OpenXML4J but is not valid.
// 	 * @throws InvalidFormatException
// 	 *             Throw if the specified part name is not conform to Open
// 	 *             Packaging Convention specifications.
// 	 * @see java.net.URI
// 	 */
//         public PackagePartName(Uri uri, bool checkConformance) {
//             if (checkConformance) {
//                 throwExceptionIfInvalidPartUri(uri);
//             }
//             else {
//                 if (!PackagingURIHelper.PACKAGE_ROOT_URI.Equals(uri)) {
//                     throw new OpenXML4JRuntimeException("OCP conformance must be check for ALL part name except special cases : ['/']");
//                 }
//             }
//
//             this.partNameURI = uri;
//             this.isRelationship = isRelationshipPartURI(this.partNameURI);
//         }
//
//         /**
// 	 * Constructor. Makes a ValidPartName object from a String part name.
// 	 *
// 	 * @param partName
// 	 *            Part name to valid and to create.
// 	 * @param checkConformance
// 	 *            Flag to specify if the contructor have to validate the OPC
// 	 *            conformance. Must be always <code>true</code> except for
// 	 *            special URI like '/' which is needed for internal use by
// 	 *            OpenXML4J but is not valid.
// 	 * @throws InvalidFormatException
// 	 *             Throw if the specified part name is not conform to Open
// 	 *             Packaging Convention specifications.
// 	 */
//         public PackagePartName(string partName, bool checkConformance) {
//             Uri partURI;
//             try {
//                 partURI = new Uri(partName);
//             }
//             catch (URISyntaxException e) {
//                 throw new ArgumentException("partName argmument is not a valid OPC part name !");
//             }
//
//             if (checkConformance) {
//                 throwExceptionIfInvalidPartUri(partURI);
//             }
//             else {
//                 if (!PackagingURIHelper.PACKAGE_ROOT_URI.Equals(partURI)) {
//                     throw new OpenXML4JRuntimeException(
//                         "OCP conformance must be check for ALL part name except special cases : ['/']");
//                 }
//             }
//
//             this.partNameURI = partURI;
//             this.isRelationship = isRelationshipPartURI(this.partNameURI);
//         }
//
//         /**
// 	 * Check if the specified part name is a relationship part name.
// 	 *
// 	 * @param partUri
// 	 *            The URI to check.
// 	 * @return <code>true</code> if this part name respect the relationship
// 	 *         part naming convention else <code>false</code>.
// 	 */
//         private bool isRelationshipPartURI(Uri partUri) {
//             if (partUri == null)
//                 throw new ArgumentException("partUri");
//
//             return Regex.IsMatch(partUri.getPath(), "^.*/" + PackagingURIHelper.RELATIONSHIP_PART_SEGMENT_NAME + "/.*\\"
//                                                     + PackagingURIHelper.RELATIONSHIP_PART_EXTENSION_NAME
//                                                     + "$");
//         }
//
//         /**
// 	 * Know if this part name is a relationship part name.
// 	 *
// 	 * @return <code>true</code> if this part name respect the relationship
// 	 *         part naming convention else <code>false</code>.
// 	 */
//         public bool isRelationshipPartURI() {
//             return this.isRelationship;
//         }
//
//         /**
// 	 * Throws an exception (of any kind) if the specified part name does not
// 	 * follow the Open Packaging Convention specifications naming rules.
// 	 *
// 	 * @param partUri
// 	 *            The part name to check.
// 	 * @throws Exception
// 	 *             Throws if the part name is invalid.
// 	 */
//         private static void throwExceptionIfInvalidPartUri(Uri partUri) {
//             if (partUri == null)
//                 throw new ArgumentException("partUri");
//             // Check if the part name URI is empty [M1.1]
//             throwExceptionIfEmptyURI(partUri);
//
//             // Check if the part name URI is absolute
//             throwExceptionIfAbsoluteUri(partUri);
//
//             // Check if the part name URI starts with a forward slash [M1.4]
//             throwExceptionIfPartNameNotStartsWithForwardSlashChar(partUri);
//
//             // Check if the part name URI ends with a forward slash [M1.5]
//             throwExceptionIfPartNameEndsWithForwardSlashChar(partUri);
//
//             // Check if the part name does not have empty segments. [M1.3]
//             // Check if a segment ends with a dot ('.') character. [M1.9]
//             throwExceptionIfPartNameHaveInvalidSegments(partUri);
//         }
//
//         /**
// 	 * Throws an exception if the specified URI is empty. [M1.1]
// 	 *
// 	 * @param partURI
// 	 *            Part URI to check.
// 	 * @throws InvalidFormatException
// 	 *             If the specified URI is empty.
// 	 */
//         private static void throwExceptionIfEmptyURI(Uri partURI) {
//             if (partURI == null)
//                 throw new ArgumentException("partURI");
//
//             String uriPath = partURI.getPath();
//             if (uriPath.Length == 0
//                 || ((uriPath.Length == 1) && (uriPath[0] == PackagingURIHelper.FORWARD_SLASH_CHAR)))
//                 throw new FormatException(
//                     "A part name shall not be empty [M1.1]: "
//                     + partURI.getPath());
//         }
//
//         /**
//      * Throws an exception if the part name has empty segments. [M1.3]
//      *
//      * Throws an exception if a segment any characters other than pchar
//      * characters. [M1.6]
//      *
//      * Throws an exception if a segment contain percent-encoded forward slash
//      * ('/'), or backward slash ('\') characters. [M1.7]
//      *
//      * Throws an exception if a segment contain percent-encoded unreserved
//      * characters. [M1.8]
//      *
//      * Throws an exception if the specified part name's segments end with a dot
//      * ('.') character. [M1.9]
//      *
//      * Throws an exception if a segment doesn't include at least one non-dot
//      * character. [M1.10]
//      *
//      * @param partUri
//      *            The part name to check.
//      * @throws InvalidFormatException
//      *             if the specified URI contain an empty segments or if one the
//      *             segments contained in the part name, ends with a dot ('.')
//      *             character.
//      */
//         private static void throwExceptionIfPartNameHaveInvalidSegments(Uri partUri) {
//             if (partUri == null) {
//                 throw new ArgumentException("partUri");
//             }
//
//             // Split the URI into several part and analyze each
//             String[] segments = partUri.EncodeUri().Split("/");
//             if (segments.Length <= 1 || !segments[0].Equals(""))
//                 throw new FormatException(
//                     "A part name shall not have empty segments [M1.3]: "
//                     + partUri.getPath());
//             for (int i = 1; i < segments.Length; ++i) {
//                 String seg = segments[i];
//                 if (seg == null || "".Equals(seg)) {
//                     throw new FormatException(
//                         "A part name shall not have empty segments [M1.3]: "
//                         + partUri.getPath());
//                 }
//
//                 if (seg.EndsWith(".")) {
//                     throw new FormatException(
//                         "A segment shall not end with a dot ('.') character [M1.9]: "
//                         + partUri.getPath());
//                 }
//
//                 if ("".Equals(seg.Replace("\\\\.", ""))) {
//                     // Normally will never been invoked with the previous
//                     // implementation rule [M1.9]
//                     throw new FormatException(
//                         "A segment shall include at least one non-dot character. [M1.10]: "
//                         + partUri.getPath());
//                 }
//
//                 // Check for rule M1.6, M1.7, M1.8
//                 checkPCharCompliance(seg);
//             }
//         }
//
//         /**
//      * Throws an exception if a segment any characters other than pchar
//      * characters. [M1.6]
//      *
//      * Throws an exception if a segment contain percent-encoded forward slash
//      * ('/'), or backward slash ('\') characters. [M1.7]
//      *
//      * Throws an exception if a segment contain percent-encoded unreserved
//      * characters. [M1.8]
//      *
//      * @param segment
//      *            The segment to check
//      */
//         private static void checkPCharCompliance(string segment) {
//             bool errorFlag;
//             for (int i = 0; i < segment.Length; ++i) {
//                 char c = segment[i];
//                 errorFlag = true;
//
//                 /* Check rule M1.6 */
//
//                 // Check for digit or letter
//                 if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
//                                            || (c >= '0' && c <= '9')) {
//                     errorFlag = false;
//                 }
//                 else {
//                     // Check "-", ".", "_", "~"
//                     for (int j = 0; j < RFC3986_PCHAR_UNRESERVED_SUP.Length; ++j) {
//                         if (c == RFC3986_PCHAR_UNRESERVED_SUP[j][0]) {
//                             errorFlag = false;
//                             break;
//                         }
//                     }
//
//                     // Check ":", "@"
//                     for (int j = 0;
//                         errorFlag
//                         && j < RFC3986_PCHAR_AUTHORIZED_SUP.Length;
//                         ++j) {
//                         if (c == RFC3986_PCHAR_AUTHORIZED_SUP[j][0]) {
//                             errorFlag = false;
//                         }
//                     }
//
//                     // Check "!", "$", "&", "'", "(", ")", "*", "+", ",", ";", "="
//                     for (int j = 0;
//                         errorFlag
//                         && j < RFC3986_PCHAR_SUB_DELIMS.Length;
//                         ++j) {
//                         if (c == RFC3986_PCHAR_SUB_DELIMS[j][0]) {
//                             errorFlag = false;
//                         }
//                     }
//                 }
//
//                 if (errorFlag && c == '%') {
//                     // We certainly found an encoded character, check for length
//                     // now ( '%' HEXDIGIT HEXDIGIT)
//                     if (((segment.Length - i) < 2)) {
//                         throw new FormatException("The segment " + segment
//                                                                  + " contain invalid encoded character !");
//                     }
//
//                     // If not percent encoded character error occur then reset the
//                     // flag -> the character is valid
//                     errorFlag = false;
//
//                     // Decode the encoded character
//                     char decodedChar = (char) Int16.Parse(segment.Substring(i + 1, 2));
//                     i += 2;
//
//                     /* Check rule M1.7 */
//                     if (decodedChar == '/' || decodedChar == '\\')
//                         throw new FormatException(
//                             "A segment shall not contain percent-encoded forward slash ('/'), or backward slash ('\') characters. [M1.7]");
//
//                     /* Check rule M1.8 */
//
//                     // Check for unreserved character like define in RFC3986
//                     if ((decodedChar >= 'A' && decodedChar <= 'Z')
//                         || (decodedChar >= 'a' && decodedChar <= 'z')
//                         || (decodedChar >= '0' && decodedChar <= '9'))
//                         errorFlag = true;
//
//                     // Check for unreserved character "-", ".", "_", "~"
//                     for (int j = 0; !errorFlag && j < RFC3986_PCHAR_UNRESERVED_SUP.Length; ++j) {
//                         if (c == RFC3986_PCHAR_UNRESERVED_SUP[j][0]) {
//                             errorFlag = true;
//                             break;
//                         }
//                     }
//
//                     if (errorFlag)
//                         throw new FormatException("A segment shall not contain percent-encoded unreserved characters. [M1.8]");
//                 }
//
//                 if (errorFlag)
//                     throw new FormatException("A segment shall not hold any characters other than pchar characters. [M1.6]");
//             }
//         }
//
//         /**
//      * Throws an exception if the specified part name doesn't start with a
//      * forward slash character '/'. [M1.4]
//      *
//      * @param partUri
//      *            The part name to check.
//      * @throws InvalidFormatException
//      *             If the specified part name doesn't start with a forward slash
//      *             character '/'.
//      */
//         private static void throwExceptionIfPartNameNotStartsWithForwardSlashChar(Uri partUri) {
//             String uriPath = partUri.getPath();
//             if (uriPath.Length > 0
//                 && uriPath[0] != PackagingURIHelper.FORWARD_SLASH_CHAR)
//                 throw new FormatException("A part name shall start with a forward slash ('/') character [M1.4]: "
//                                           + partUri.getPath());
//         }
//
//         /**
//      * Throws an exception if the specified part name ends with a forwar slash
//      * character '/'. [M1.5]
//      *
//      * @param partUri
//      *            The part name to check.
//      * @throws InvalidFormatException
//      *             If the specified part name ends with a forwar slash character
//      *             '/'.
//      */
//         private static void throwExceptionIfPartNameEndsWithForwardSlashChar(Uri partUri) {
//             String uriPath = partUri.getPath();
//             if (uriPath.Length > 0
//                 && uriPath[uriPath.Length - 1] == PackagingURIHelper.FORWARD_SLASH_CHAR)
//                 throw new FormatException("A part name shall not have a forward slash as the last character [M1.5]: "
//                                           + partUri.getPath());
//         }
//
//         /**
//      * Throws an exception if the specified URI is absolute.
//      *
//      * @param partUri
//      *            The URI to check.
//      * @throws InvalidFormatException
//      *             Throws if the specified URI is absolute.
//      */
//         private static void throwExceptionIfAbsoluteUri(Uri partUri) {
//             if (partUri.IsAbsoluteUri)
//                 throw new FormatException("Absolute URI forbidden: "
//                                           + partUri);
//         }
//
//
//         /**
//  * Retrieves the extension of the part name if any. If there is no extension
//  * returns an empty String. Example : '/document/content.xml' => 'xml'
//  *
//  * @return The extension of the part name.
//  */
//         public String getExtension() {
//             String fragment = this.partNameURI.getPath();
//             if (fragment.Length > 0) {
//                 int i = fragment.LastIndexOf(".");
//                 if (i > -1)
//                     return fragment.Substring(i + 1);
//             }
//
//             return "";
//         }
//
//         /**
//  * Get this part name.
//  *
//  * @return The name of this part name.
//  */
//         public String getName() {
//             return this.partNameURI.EncodeUri();
//         }
//
//         /**
//  * Part name equivalence is determined by comparing part names as
//  * case-insensitive ASCII strings. Packages shall not contain equivalent
//  * part names and package implementers shall neither create nor recognize
//  * packages with equivalent part names. [M1.12]
//  */
//         public override bool Equals(Object otherPartName) {
//             if (otherPartName == null || !(otherPartName is PackagePartName))
//                 return false;
//             return this.partNameURI.EncodeUri().ToLower().Equals(((PackagePartName) otherPartName).partNameURI.EncodeUri().ToLower());
//         }
//
//         public int hashCode() {
//             return this.partNameURI.EncodeUri().ToLower().GetHashCode();
//         }
//
//         public String toString() {
//             return getName();
//         }
//
// /* Getters and setters */
//
//         /**
//  * Part name property getter.
//  *
//  * @return This part name URI.
//  */
//         public Uri getURI() {
//             return this.partNameURI;
//         }
//
//         /**
//      * Compare two part name following the rule M1.12 :
//      *
//      * Part name equivalence is determined by comparing part names as
//      * case-insensitive ASCII strings. Packages shall not contain equivalent
//      * part names and package implementers shall neither create nor recognize
//      * packages with equivalent part names. [M1.12]
//      */
//         public int CompareTo(PackagePartName? otherPartName) {
//             if (otherPartName == null)
//                 return -1;
//             return this.partNameURI.EncodeUri().ToLower().CompareTo(otherPartName.partNameURI.EncodeUri().ToLower());
//         }
//     }
// }