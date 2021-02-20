// using System;
// using System.Web;
//
// namespace DevIgnite.Apache.Excel.Port {
//     public static class OpenXmlExtensions {
//         public static string EncodeUri(this Uri uri) {
//             return HttpUtility.UrlEncode(uri.ToString());
//         }
//
//         public static string Decode(this Uri uri) {
//             return HttpUtility.UrlDecode(uri.ToString());
//         }
//
//         public static string getPath(this Uri uri) {
//             return uri.Decode();
//         }
//
//         // RFC2396 5.2
//         private static Uri resolve(Uri @base, Uri child) {
//             // check if child if opaque first so that NPE is thrown
//             // if child is null.
//
//             if (child.PathAndQuery == null || @base.PathAndQuery == null)
//                 return child;
//
//             // 5.2 (2): Reference to current document (lone fragment)
//             if (String.IsNullOrEmpty(child.Scheme) && (String.IsNullOrEmpty(child.Authority))
//                                                    && String.IsNullOrEmpty(child.PathAndQuery) && (String.IsNullOrEmpty(child.Fragment)
//                                                                                                    && String.IsNullOrEmpty(child.PathAndQuery))) {
//                 if (!String.IsNullOrEmpty(@base.Fragment)
//                     && child.Fragment.Equals(@base.Fragment)) {
//                     return @base;
//                 }
//
//                 Uri ru = new Uri();
//                 ru.Scheme = @base.Scheme;
//                 ru.Authority = @base.Authority;
//                 ru.UserInfo = @base.UserInfo;
//                 ru.host = @base.host;
//                 ru.port = @base.port;
//                 ru.path = @base.path;
//                 ru.fragment = child.fragment;
//                 ru.query = @base.query;
//                 return ru;
//             }
//
//             // 5.2 (3): Child is absolute
//             if (child.scheme != null)
//                 return child;
//
//             URI ru = new URI(); // Resolved URI
//             ru.scheme = base.scheme;
//             ru.query = child.query;
//             ru.fragment = child.fragment;
//
//             // 5.2 (4): Authority
//             if (child.authority == null) {
//                 ru.authority = base.authority;
//                 ru.host = base.host;
//                 ru.userInfo = base.userInfo;
//                 ru.port = base.port;
//
//                 String cp = (child.path == null) ? "" : child.path;
//                 if (!cp.isEmpty() && cp.charAt(0) == '/') {
//                     // 5.2 (5): Child path is absolute
//                     ru.path = child.path;
//                 }
//                 else {
//                     // 5.2 (6): Resolve relative path
//                     ru.path = resolvePath(base.path, cp, base.isAbsolute());
//                 }
//             }
//             else {
//                 ru.authority = child.authority;
//                 ru.host = child.host;
//                 ru.userInfo = child.userInfo;
//                 ru.host = child.host;
//                 ru.port = child.port;
//                 ru.path = child.path;
//             }
//
//             // 5.2 (7): Recombine (nothing to do here)
//             return ru;
//         }
//     }
// }