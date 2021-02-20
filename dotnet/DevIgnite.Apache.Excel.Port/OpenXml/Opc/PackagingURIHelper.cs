// using System;
// using System.Text;
// using System.Text.RegularExpressions;
// using DevIgnite.Apache.Excel.Port.OpenXml.Opc;
//
// namespace DevIgnite.Apache.Excel.Port.OpenXml.Opc {
//     public class PackagingURIHelper {
//         /**
// 	 * Package root URI.
// 	 */
//         private static Uri packageRootUri;
//
//         /**
// 	 * Extension name of a relationship part.
// 	 */
//         public static string RELATIONSHIP_PART_EXTENSION_NAME;
//
//         /**
// 	 * Segment name of a relationship part.
// 	 */
//         public static string RELATIONSHIP_PART_SEGMENT_NAME;
//
//         /**
// 	 * Segment name of the package properties folder.
// 	 */
//         public static string PACKAGE_PROPERTIES_SEGMENT_NAME;
//
//         /**
// 	 * Core package properties art name.
// 	 */
//         public static string PACKAGE_CORE_PROPERTIES_NAME;
//
//         /**
// 	 * Forward slash URI separator.
// 	 */
//         public static char FORWARD_SLASH_CHAR;
//
//         /**
// 	 * Forward slash URI separator.
// 	 */
//         public static string FORWARD_SLASH_STRING;
//
//         /**
// 	 * Package relationships part URI
// 	 */
//         public static Uri PACKAGE_RELATIONSHIPS_ROOT_URI;
//
//         /**
// 	 * Package relationships part name.
// 	 */
//         public static PackagePartName PACKAGE_RELATIONSHIPS_ROOT_PART_NAME;
//
//         /**
// 	 * Core properties part URI.
// 	 */
//         public static Uri CORE_PROPERTIES_URI;
//
//         /**
// 	 * Core properties partname.
// 	 */
//         public static PackagePartName CORE_PROPERTIES_PART_NAME;
//
//         /**
// 	 * Root package URI.
// 	 */
//         public static Uri PACKAGE_ROOT_URI;
//
//         /**
// 	 * Root package part name.
// 	 */
//         public static PackagePartName PACKAGE_ROOT_PART_NAME;
//
//         /* Static initialization */
//         static PackagingURIHelper() {
//             RELATIONSHIP_PART_SEGMENT_NAME = "_rels";
//             RELATIONSHIP_PART_EXTENSION_NAME = ".rels";
//             FORWARD_SLASH_CHAR = '/';
//             FORWARD_SLASH_STRING = "/";
//             PACKAGE_PROPERTIES_SEGMENT_NAME = "docProps";
//             PACKAGE_CORE_PROPERTIES_NAME = "core.xml";
//
//             // Make URI
//             Uri uriPACKAGE_ROOT_URI = null;
//             Uri uriPACKAGE_RELATIONSHIPS_ROOT_URI = null;
//             Uri uriPACKAGE_PROPERTIES_URI = null;
//             try {
//                 uriPACKAGE_ROOT_URI = new Uri("/");
//                 uriPACKAGE_RELATIONSHIPS_ROOT_URI = new Uri(FORWARD_SLASH_CHAR
//                                                             + RELATIONSHIP_PART_SEGMENT_NAME + FORWARD_SLASH_CHAR
//                                                             + RELATIONSHIP_PART_EXTENSION_NAME);
//                 packageRootUri = new Uri("/");
//                 uriPACKAGE_PROPERTIES_URI = new Uri(FORWARD_SLASH_CHAR
//                                                     + PACKAGE_PROPERTIES_SEGMENT_NAME + FORWARD_SLASH_CHAR
//                                                     + PACKAGE_CORE_PROPERTIES_NAME);
//             }
//             catch (Exception e) {
//                 // Should never happen in production as all data are fixed
//             }
//
//             PACKAGE_ROOT_URI = uriPACKAGE_ROOT_URI;
//             PACKAGE_RELATIONSHIPS_ROOT_URI = uriPACKAGE_RELATIONSHIPS_ROOT_URI;
//             CORE_PROPERTIES_URI = uriPACKAGE_PROPERTIES_URI;
//
//             // Make part name from previous URI
//             PackagePartName tmpPACKAGE_ROOT_PART_NAME = null;
//             PackagePartName tmpPACKAGE_RELATIONSHIPS_ROOT_PART_NAME = null;
//             PackagePartName tmpCORE_PROPERTIES_URI = null;
//             try {
//                 tmpPACKAGE_RELATIONSHIPS_ROOT_PART_NAME = createPartName(PACKAGE_RELATIONSHIPS_ROOT_URI);
//                 tmpCORE_PROPERTIES_URI = createPartName(CORE_PROPERTIES_URI);
//                 tmpPACKAGE_ROOT_PART_NAME = new PackagePartName(PACKAGE_ROOT_URI,
//                     false);
//             }
//             catch (FormatException e) {
//                 // Should never happen in production as all data are fixed
//             }
//
//             PACKAGE_RELATIONSHIPS_ROOT_PART_NAME = tmpPACKAGE_RELATIONSHIPS_ROOT_PART_NAME;
//             CORE_PROPERTIES_PART_NAME = tmpCORE_PROPERTIES_URI;
//             PACKAGE_ROOT_PART_NAME = tmpPACKAGE_ROOT_PART_NAME;
//         }
//
//         private static Regex missingAuthPattern = new Regex("\\w+://", RegexOptions.Compiled);
//
//         /**
// 	 * Gets the URI for the package root.
// 	 *
// 	 * @return URI of the package root.
// 	 */
//         public static Uri getPackageRootUri() {
//             return packageRootUri;
//         }
//
//         /**
// 	 * Know if the specified URI is a relationship part name.
// 	 *
// 	 * @param partUri
// 	 *            URI to check.
// 	 * @return <i>true</i> if the URI <i>false</i>.
// 	 */
//         public static bool isRelationshipPartURI(Uri partUri) {
//             if (partUri == null)
//                 throw new ArgumentException("partUri");
//
//             return Regex.IsMatch(partUri.getPath(), ".*" + RELATIONSHIP_PART_SEGMENT_NAME + ".*"
//                 + RELATIONSHIP_PART_EXTENSION_NAME + "$");
//         }
//
//         /**
// 	 * Get file name from the specified URI.
// 	 */
//         public static String getFilename(Uri uri) {
//             if (uri != null) {
//                 String path = uri.getPath();
//                 int len = path.Length;
//                 int num2 = len;
//                 while (--num2 >= 0) {
//                     char ch1 = path[num2];
//                     if (ch1 == PackagingURIHelper.FORWARD_SLASH_CHAR)
//                         //TODO: Substring is LastIndex not length
//                         return path.Substring(num2 + 1, len);
//                 }
//             }
//
//             return "";
//         }
//
//         /**
// 	 * Get the file name without the trailing extension.
// 	 */
//         public static String getFilenameWithoutExtension(Uri uri) {
//             String filename = getFilename(uri);
//             int dotIndex = filename.LastIndexOf(".");
//             if (dotIndex == -1)
//                 return filename;
//             //TODO: Substring is LastIndex not length
//             return filename.Substring(0, dotIndex);
//         }
//
//         /**
// 	 * Get the directory path from the specified URI.
// 	 */
//         public static Uri getPath(Uri uri) {
//             if (uri != null) {
//                 String path = uri.getPath();
//                 int len = path.Length;
//                 int num2 = len;
//                 while (--num2 >= 0) {
//                     char ch1 = path[num2];
//                     if (ch1 == PackagingURIHelper.FORWARD_SLASH_CHAR) {
//                         try {
//                             //TODO: Substring is LastIndex not length
//                             return new Uri(path.Substring(0, num2));
//                         }
//                         catch (URISyntaxException e) {
//                             return null;
//                         }
//                     }
//                 }
//             }
//
//             return null;
//         }
//
//         /**
// 	 * Combine two URIs.
// 	 *
// 	 * @param prefix the prefix URI
// 	 * @param suffix the suffix URI
// 	 *
// 	 * @return the combined URI
// 	 */
//         public static Uri combine(Uri prefix, Uri suffix) {
//             Uri retUri = null;
//             try {
//                 retUri = new Uri(combine(prefix.getPath(), suffix.getPath()));
//             }
//             catch (URISyntaxException e) {
//                 throw new ArgumentException(
//                     "Prefix and suffix can't be combine !");
//             }
//
//             return retUri;
//         }
//
//         /**
// 	 * Combine a string URI with a prefix and a suffix.
// 	 */
//         public static String combine(String prefix, String suffix) {
//             if (!prefix.EndsWith("" + FORWARD_SLASH_CHAR)
//                 && !suffix.StartsWith("" + FORWARD_SLASH_CHAR))
//                 return prefix + FORWARD_SLASH_CHAR + suffix;
//             else if ((!prefix.EndsWith("" + FORWARD_SLASH_CHAR)
//                 && suffix.StartsWith("" + FORWARD_SLASH_CHAR) || (prefix
//                     .EndsWith("" + FORWARD_SLASH_CHAR) && !suffix.StartsWith(""
//                                                                              + FORWARD_SLASH_CHAR))))
//                 return prefix + suffix;
//             else
//                 return "";
//         }
//
//         /**
// 	 * Fully relativize the source part URI against the target part URI.
// 	 *
// 	 * @param sourceURI
// 	 *            The source part URI.
// 	 * @param targetURI
// 	 *            The target part URI.
//      * @param  msCompatible if true then remove leading slash from the relativized URI.
//      *         This flag violates [M1.4]: A part name shall start with a forward slash ('/') character, but
//      *         allows generating URIs compatible with MS Office and OpenOffice.
// 	 * @return A fully relativize part name URI ('word/media/image1.gif',
// 	 *         '/word/document.xml' => 'media/image1.gif') else
// 	 *         <code>null</code>.
// 	 */
//         public static Uri relativizeURI(Uri sourceURI, Uri targetURI, bool msCompatible) {
//             StringBuilder retVal = new StringBuilder();
//             String[] segmentsSource = sourceURI.getPath().Split("/", -1);
//             String[] segmentsTarget = targetURI.getPath().Split("/", -1);
//
//             // If the source URI is empty
//             if (segmentsSource.Length == 0) {
//                 throw new ArgumentException(
//                     "Can't relativize an empty source URI !");
//             }
//
//             // If target URI is empty
//             if (segmentsTarget.Length == 0) {
//                 throw new ArgumentException(
//                     "Can't relativize an empty target URI !");
//             }
//
//             // If the source is the root, then the relativized
//             //  form must actually be an absolute URI
//             if (sourceURI.ToString().Equals("/")) {
//                 String path = targetURI.getPath();
//                 if (msCompatible && path.Length > 0 && path[0] == '/') {
//                     try {
//                         targetURI = new Uri(path.Substring(1));
//                     }
//                     catch (Exception e) {
//                         return null;
//                     }
//                 }
//
//                 return targetURI;
//             }
//
//
//             // Relativize the source URI against the target URI.
//             // First up, figure out how many steps along we can go
//             // and still have them be the same
//             int segmentsTheSame = 0;
//             for (int i = 0; i < segmentsSource.Length && i < segmentsTarget.Length; i++) {
//                 if (segmentsSource[i].Equals(segmentsTarget[i])) {
//                     // Match so far, good
//                     segmentsTheSame++;
//                 }
//                 else {
//                     break;
//                 }
//             }
//
//             // If we didn't have a good match or at least except a first empty element
//             if ((segmentsTheSame == 0 || segmentsTheSame == 1) &&
//                 segmentsSource[0].Equals("") && segmentsTarget[0].Equals("")) {
//                 for (int i = 0; i < segmentsSource.Length - 2; i++) {
//                     retVal.Append("../");
//                 }
//
//                 for (int i = 0; i < segmentsTarget.Length; i++) {
//                     if (segmentsTarget[i].Equals(""))
//                         continue;
//                     retVal.Append(segmentsTarget[i]);
//                     if (i != segmentsTarget.Length - 1)
//                         retVal.Append("/");
//                 }
//
//                 try {
//                     return new Uri(retVal.ToString());
//                 }
//                 catch (Exception e) {
//                     return null;
//                 }
//             }
//
//             // Special case for where the two are the same
//             if (segmentsTheSame == segmentsSource.Length
//                 && segmentsTheSame == segmentsTarget.Length) {
//                 if (sourceURI.Equals(targetURI)) {
//                     // if source and target are the same they should be resolved to the last segment,
//                     // Example: if a slide references itself, e.g. the source URI is
//                     // "/ppt/slides/slide1.xml" and the targetURI is "slide1.xml" then
//                     // this it should be relativized as "slide1.xml", i.e. the last segment.
//                     retVal.Append(segmentsSource[segmentsSource.Length - 1]);
//                 }
//                 else {
//                     retVal.Append("");
//                 }
//             }
//             else {
//                 // Matched for so long, but no more
//
//                 // Do we need to go up a directory or two from
//                 // the source to get here?
//                 // (If it's all the way up, then don't bother!)
//                 if (segmentsTheSame == 1) {
//                     retVal.Append("/");
//                 }
//                 else {
//                     for (int j = segmentsTheSame; j < segmentsSource.Length - 1; j++) {
//                         retVal.Append("../");
//                     }
//                 }
//
//                 // Now go from here on down
//                 for (int j = segmentsTheSame; j < segmentsTarget.Length; j++) {
//                     if (retVal.Length > 0
//                         && retVal[retVal.Length - 1] != '/') {
//                         retVal.Append("/");
//                     }
//
//                     retVal.Append(segmentsTarget[j]);
//                 }
//             }
//
//             // if the target had a fragment then append it to the result
//             String fragment = targetURI.Fragment;
//             if (fragment != null) {
//                 retVal.Append("#").Append(fragment);
//             }
//
//             try {
//                 return new Uri(retVal.ToString());
//             }
//             catch (Exception e) {
//                 return null;
//             }
//         }
//
//         /**
//      * Fully relativize the source part URI against the target part URI.
//      *
//      * @param sourceURI
//      *            The source part URI.
//      * @param targetURI
//      *            The target part URI.
//      * @return A fully relativize part name URI ('word/media/image1.gif',
//      *         '/word/document.xml' => 'media/image1.gif') else
//      *         <code>null</code>.
//      */
//         public static Uri relativizeURI(Uri sourceURI, Uri targetURI) {
//             return relativizeURI(sourceURI, targetURI, false);
//         }
//
//         /**
// 	 * Resolve a source uri against a target.
// 	 *
// 	 * @param sourcePartUri
// 	 *            The source URI.
// 	 * @param targetUri
// 	 *            The target URI.
// 	 * @return The resolved URI.
// 	 */
//         public static Uri resolvePartUri(Uri sourcePartUri, Uri targetUri) {
//             if (sourcePartUri == null || sourcePartUri.IsAbsoluteUri) {
//                 throw new ArgumentException("sourcePartUri invalid - "
//                                                    + sourcePartUri);
//             }
//
//             if (targetUri == null || targetUri.IsAbsoluteUri) {
//                 throw new ArgumentException("targetUri invalid - "
//                                             + targetUri);
//             }
//             
//             return sourcePartUri.resolve(targetUri);
//         }
//
//         /**
// 	 * Get URI from a string path.
// 	 */
//         public static Uri getURIFromPath(String path) {
//             Uri retUri;
//             try {
//                 retUri = toURI(path);
//             }
//             catch (URISyntaxException e) {
//                 throw new IllegalArgumentException("path");
//             }
//
//             return retUri;
//         }
//
//         /**
// 	 * Get the source part URI from a specified relationships part.
// 	 *
// 	 * @param relationshipPartUri
// 	 *            The relationship part use to retrieve the source part.
// 	 * @return The source part URI from the specified relationships part.
// 	 */
//         public static Uri getSourcePartUriFromRelationshipPartUri(
//             Uri relationshipPartUri) {
//             if (relationshipPartUri == null)
//                 throw new IllegalArgumentException(
//                     "Must not be null");
//
//             if (!isRelationshipPartURI(relationshipPartUri))
//                 throw new IllegalArgumentException(
//                     "Must be a relationship part");
//
//             if (relationshipPartUri.compareTo(PACKAGE_RELATIONSHIPS_ROOT_URI) == 0)
//                 return PACKAGE_ROOT_URI;
//
//             String filename = relationshipPartUri.getPath();
//             String filenameWithoutExtension = getFilenameWithoutExtension(relationshipPartUri);
//             filename = filename
//                 .substring(0, ((filename.length() - filenameWithoutExtension
//                     .length()) - RELATIONSHIP_PART_EXTENSION_NAME.length()));
//             filename = filename.substring(0, filename.length()
//                                              - RELATIONSHIP_PART_SEGMENT_NAME.length() - 1);
//             filename = combine(filename, filenameWithoutExtension);
//             return getURIFromPath(filename);
//         }
//
//         /**
// 	 * Create an OPC compliant part name by throwing an exception if the URI is
// 	 * not valid.
// 	 *
// 	 * @param partUri
// 	 *            The part name URI to validate.
// 	 * @return A valid part name object, else <code>null</code>.
// 	 * @throws InvalidFormatException
// 	 *             Throws if the specified URI is not OPC compliant.
// 	 */
//         public static PackagePartName createPartName(Uri partUri)
//
//         throws InvalidFormatException {
//             if (partUri == null)
//         throw new IllegalArgumentException("partName");
//             return new PackagePartName(partUri, true);
//     }
//
//     /**
//      * Create an OPC compliant part name.
//      *
//      * @param partName
//      *            The part name to validate.
//      * @return The correspondant part name if valid, else <code>null</code>.
//      * @throws InvalidFormatException
//      *             Throws if the specified part name is not OPC compliant.
//      * @see #createPartName(URI)
//      */
//     public static PackagePartName createPartName(String partName)
//     throws InvalidFormatException {
//     Uri partNameURI;
//         try {
//         partNameURI = toURI(partName);
//     } catch (URISyntaxException e) {
//     throw new InvalidFormatException(e.getMessage());
// }
//
// return createPartName(partNameURI);
// }
//
// /**
//  * Create an OPC compliant part name by resolving it using a base part.
//  *
//  * @param partName
//  *            The part name to validate.
//  * @param relativePart
//  *            The relative base part.
//  * @return The correspondant part name if valid, else <code>null</code>.
//  * @throws InvalidFormatException
//  *             Throws if the specified part name is not OPC compliant.
//  * @see #createPartName(URI)
//  */
// public static PackagePartName createPartName(String partName,
//     PackagePart relativePart)
//
// throws InvalidFormatException {
//     Uri newPartNameURI;
//     try {
//         newPartNameURI = resolvePartUri(
//             relativePart.getPartName().getURI(), new Uri(partName));
//     }
//     catch (URISyntaxException e) {
//         throw new InvalidFormatException(e.getMessage());
//     }
//
//     return createPartName(newPartNameURI);
// }
//
// /**
//  * Create an OPC compliant part name by resolving it using a base part.
//  *
//  * @param partName
//  *            The part name URI to validate.
//  * @param relativePart
//  *            The relative base part.
//  * @return The correspondant part name if valid, else <code>null</code>.
//  * @throws InvalidFormatException
//  *             Throws if the specified part name is not OPC compliant.
//  * @see #createPartName(URI)
//  */
// public static PackagePartName createPartName(Uri partName,
//     PackagePart relativePart)
//
// throws InvalidFormatException {
//     Uri newPartNameURI = resolvePartUri(
//         relativePart.getPartName().getURI(), partName);
//     return createPartName(newPartNameURI);
// }
//
// /**
//  * Validate a part URI by returning a boolean.
//  * ([M1.1],[M1.3],[M1.4],[M1.5],[M1.6])
//  *
//  * (OPC Specifications 8.1.1 Part names) :
//  *
//  * Part Name Syntax
//  *
//  * The part name grammar is defined as follows:
//  *
//  * <i>part_name = 1*( "/" segment )
//  *
//  * segment = 1*( pchar )</i>
//  *
//  *
//  * (pchar is defined in RFC 3986)
//  *
//  * @param partUri
//  *            The URI to validate.
//  * @return <b>true</b> if the URI is valid to the OPC Specifications, else
//  *         <b>false</b>
//  *
//  * @see #createPartName(URI)
//  */
// public static boolean isValidPartName(Uri partUri) {
//     if (partUri == null)
//         throw new IllegalArgumentException("partUri");
//
//     try {
//         createPartName(partUri);
//         return true;
//     }
//     catch (Exception e) {
//         return false;
//     }
// }
//
// /**
//  * Decode a URI by converting all percent encoded character into a String
//  * character.
//  *
//  * @param uri
//  *            The URI to decode.
//  * @return The specified URI in a String with converted percent encoded
//  *         characters.
//  */
// public static String decodeURI(Uri uri) {
//     StringBuffer retVal = new StringBuffer();
//     String uriStr = uri.toASCIIString();
//     char c;
//     for (int i = 0; i < uriStr.length(); ++i) {
//         c = uriStr.charAt(i);
//         if (c == '%') {
//             // We certainly found an encoded character, check for length
//             // now ( '%' HEXDIGIT HEXDIGIT)
//             if (((uriStr.length() - i) < 2)) {
//                 throw new IllegalArgumentException("The uri " + uriStr
//                                                               + " contain invalid encoded character !");
//             }
//
//             // Decode the encoded character
//             char decodedChar = (char) Integer.parseInt(uriStr.substring(
//                 i + 1, i + 3), 16);
//             retVal.append(decodedChar);
//             i += 2;
//             continue;
//         }
//
//         retVal.append(c);
//     }
//
//     return retVal.toString();
// }
//
// /**
//  * Build a part name where the relationship should be stored ((ex
//  * /word/document.xml -> /word/_rels/document.xml.rels)
//  *
//  * @param partName
//  *            Source part URI
//  * @return the full path (as URI) of the relation file
//  * @throws InvalidOperationException
//  *             Throws if the specified URI is a relationshp part.
//  */
// public static PackagePartName getRelationshipPartName(
//     PackagePartName partName) {
//     if (partName == null)
//         throw new IllegalArgumentException("partName");
//
//     if (PackagingURIHelper.PACKAGE_ROOT_URI.getPath().equals(
//         partName.getURI().getPath()))
//         return PackagingURIHelper.PACKAGE_RELATIONSHIPS_ROOT_PART_NAME;
//
//     if (partName.isRelationshipPartURI())
//         throw new InvalidOperationException("Can't be a relationship part");
//
//     String fullPath = partName.getURI().getPath();
//     String filename = getFilename(partName.getURI());
//     fullPath = fullPath.substring(0, fullPath.length() - filename.length());
//     fullPath = combine(fullPath,
//         PackagingURIHelper.RELATIONSHIP_PART_SEGMENT_NAME);
//     fullPath = combine(fullPath, filename);
//     fullPath = fullPath
//                + PackagingURIHelper.RELATIONSHIP_PART_EXTENSION_NAME;
//
//     PackagePartName retPartName;
//     try {
//         retPartName = createPartName(fullPath);
//     }
//     catch (InvalidFormatException e) {
//         // Should never happen in production as all data are fixed but in
//         // case of return null:
//         return null;
//     }
//
//     return retPartName;
// }
//
// /**
//  * Convert a string to {@link java.net.URI}
//  *
//  * If  part name is not a valid URI, it is resolved as follows:
//  * <p>
//  * 1. Percent-encode each open bracket ([) and close bracket (]).</li>
//  * 2. Percent-encode each percent (%) character that is not followed by a hexadecimal notation of an octet value.</li>
//  * 3. Un-percent-encode each percent-encoded unreserved character.
//  * 4. Un-percent-encode each forward slash (/) and back slash (\).
//  * 5. Convert all back slashes to forward slashes.
//  * 6. If present in a segment containing non-dot (?.?) characters, remove trailing dot (?.?) characters from each segment.
//  * 7. Replace each occurrence of multiple consecutive forward slashes (/) with a single forward slash.
//  * 8. If a single trailing forward slash (/) is present, remove that trailing forward slash.
//  * 9. Remove complete segments that consist of three or more dots.
//  * 10. Resolve the relative reference against the base URI of the part holding the Unicode string, as it is defined
//  * in ?5.2 of RFC 3986. The path component of the resulting absolute URI is the part name.
//  *</p>
//  *
//  * @param   value   the string to be parsed into a URI
//  * @return  the resolved part name that should be OK to construct a URI
//  *
//  * TODO YK: for now this method does only (5). Finish the rest.
//  */
// public static Uri toURI(String value)
// throws URISyntaxException {
//     //5. Convert all back slashes to forward slashes
//     if (value.indexOf("\\") != -1) {
//         value = value.replace('\\', '/');
//     }
//
//     // URI fragemnts (those starting with '#') are not encoded
//     // and may contain white spaces and raw unicode characters
//     int fragmentIdx = value.indexOf('#');
//     if (fragmentIdx != -1) {
//         String path = value.substring(0, fragmentIdx);
//         String fragment = value.substring(fragmentIdx + 1);
//
//         value = path + "#" + encode(fragment);
//     }
//
//     // trailing white spaces must be url-encoded, see Bugzilla 53282
//     if (value.length() > 0) {
//         StringBuilder b = new StringBuilder();
//         int idx = value.length() - 1;
//         for (; idx >= 0; idx--) {
//             char c = value.charAt(idx);
//             if (Character.isWhitespace(c) || c == '\u00A0') {
//                 b.append(c);
//             }
//             else {
//                 break;
//             }
//         }
//
//         if (b.length() > 0) {
//             value = value.substring(0, idx + 1) + encode(b.reverse().toString());
//         }
//     }
//
//     // MS Office can insert URIs with missing authority, e.g. "http://" or "javascript://"
//     // append a forward slash to avoid parse exception
//     if (missingAuthPattern.matcher(value).matches()) {
//         value += "/";
//     }
//
//     return new Uri(value);
// }
//
// /**
//  * percent-encode white spaces and characters above 0x80.
//  * <p>
//  *   Examples:
//  *   'Apache POI' --> 'Apache%20POI'
//  *   'Apache\u0410POI' --> 'Apache%04%10POI'
//  *
//  * @param s the string to encode
//  * @return  the encoded string
//  */
// public static String encode(String s) {
//     int n = s.length();
//     if (n == 0) return s;
//
//     ByteBuffer bb;
//     try {
//         bb = ByteBuffer.wrap(s.getBytes("UTF-8"));
//     }
//     catch (UnsupportedEncodingException e) {
//         // should not happen
//         throw new RuntimeException(e);
//     }
//
//     StringBuilder sb = new StringBuilder();
//     while (bb.hasRemaining()) {
//         int b = bb.get() & 0xff;
//         if (isUnsafe(b)) {
//             sb.append('%');
//             sb.append(hexDigits[(b >> 4) & 0x0F]);
//             sb.append(hexDigits[(b >> 0) & 0x0F]);
//         }
//         else {
//             sb.append((char) b);
//         }
//     }
//
//     return sb.toString();
// }
//
// private static char[] hexDigits = {
//     '0', '1', '2', '3', '4', '5', '6', '7',
//     '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
// };
//
// private static bool isUnsafe(int ch) {
//     return ch > 0x80 || Character.isWhitespace(ch) || ch == '\u00A0';
// }
//
// }