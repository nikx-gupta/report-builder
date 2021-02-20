// using System;
// using System.Text;
//
// namespace DevIgnite.Apache.Excel.Port.OpenXml.Opc {
//     /// <summary>
//     /// A part relationship
//     /// </summary>
//     public class PackageRelationship {
//         private static Uri containerRelationshipPart = new Uri("/_rels/.rels");
//
//         /* XML markup */
//         public static string ID_ATTRIBUTE_NAME = "Id";
//         public static string RELATIONSHIPS_TAG_NAME = "Relationships";
//         public static string RELATIONSHIP_TAG_NAME = "Relationship";
//         public static string TARGET_ATTRIBUTE_NAME = "Target";
//         public static string TARGET_MODE_ATTRIBUTE_NAME = "TargetMode";
//
//         public static string TYPE_ATTRIBUTE_NAME = "Type";
//         /* End XML markup */
//
//         private String id;
//
//         private OPCPackage container;
//
//         /**
// 	 * Type de relation.
// 	 */
//         private String relationshipType;
//
//         /**
// 	 * Partie source de cette relation.
// 	 */
//         private PackagePart source;
//
//         /**
// 	 * Le mode de ciblage [Internal|External]
// 	 */
//         private TargetMode targetMode;
//
//         /**
// 	 * URI de la partie cible.
// 	 */
//         private Uri targetUri;
//
//         /**
// 	 * Constructor.
// 	 *
// 	 * @param pkg
// 	 * @param sourcePart
// 	 * @param targetUri
// 	 * @param targetMode
// 	 * @param relationshipType
// 	 * @param id
// 	 */
//         public PackageRelationship(OPCPackage pkg, PackagePart sourcePart,
//             Uri targetUri, TargetMode targetMode, String relationshipType,
//             String id) {
//             if (pkg == null)
//                 throw new IllegalArgumentException("pkg");
//             if (targetUri == null)
//                 throw new IllegalArgumentException("targetUri");
//             if (relationshipType == null)
//                 throw new IllegalArgumentException("relationshipType");
//             if (id == null)
//                 throw new IllegalArgumentException("id");
//
//             this.container = pkg;
//             this.source = sourcePart;
//             this.targetUri = targetUri;
//             this.targetMode = targetMode;
//             this.relationshipType = relationshipType;
//             this.id = id;
//         }
//
//         public bool equals(Object obj) {
//             if (!(obj is PackageRelationship)) {
//                 return false;
//             }
//             PackageRelationship rel = (PackageRelationship) obj;
//             return (this.id.Equals(rel.id)
//                     && this.relationshipType.Equals(rel.relationshipType)
//                     && (rel.source != null ? rel.source.equals(this.source) : true)
//                     && this.targetMode == rel.targetMode && this.targetUri
//                         .Equals(rel.targetUri));
//         }
//
//         public override int GetHashCode() {
//             return this.id.GetHashCode()
//                    + this.relationshipType.GetHashCode()
//                    + (this.source == null ? 0 : this.source.GetHashCode())
//                    + this.targetMode.GetHashCode()
//                    + this.targetUri.GetHashCode();
//         }
//
//         /* Getters */
//
//         public static Uri getContainerPartRelationship() {
//             return containerRelationshipPart;
//         }
//
//         /**
// 	 * @return the container
// 	 */
//         public OPCPackage getPackage() {
//             return container;
//         }
//
//         /**
// 	 * @return the id
// 	 */
//         public String getId() {
//             return id;
//         }
//
//         /**
// 	 * @return the relationshipType
// 	 */
//         public String getRelationshipType() {
//             return relationshipType;
//         }
//
//         /**
// 	 * @return the source
// 	 */
//         public PackagePart getSource() {
//             return source;
//         }
//
//         /**
// 	 *
// 	 * @return URL of the source part of this relationship
// 	 */
//         public Uri getSourceURI() {
//             if (source == null) {
//                 return PackagingURIHelper.PACKAGE_ROOT_URI;
//             }
//
//             return source._partName.getURI();
//         }
//
//         /**
// 	 * public URI getSourceUri(){ }
// 	 *
// 	 * @return the targetMode
// 	 */
//         public TargetMode getTargetMode() {
//             return targetMode;
//         }
//
//         /**
// 	 * @return the targetUri
// 	 */
//         public Uri getTargetURI() {
//             // If it's an external target, we don't
//             //  need to apply our normal validation rules
//             if (targetMode == TargetMode.EXTERNAL) {
//                 return targetUri;
//             }
//
//             // Internal target
//             // If it isn't absolute, resolve it relative
//             //  to ourselves
//             if (!targetUri.EncodeUri().StartsWith("/")) {
//                 // So it's a relative part name, try to resolve it
//                 return PackagingURIHelper.resolvePartUri(getSourceURI(), targetUri);
//             }
//
//             return targetUri;
//         }
//
//         public override string ToString() {
//             StringBuilder sb = new StringBuilder();
//             sb.Append(id == null ? "id=null" : "id=" + id);
//             sb.Append(container == null
//                 ? " - container=null"
//                 : " - container="
//                   + container.toString());
//             sb.Append(relationshipType == null
//                 ? " - relationshipType=null"
//                 : " - relationshipType=" + relationshipType);
//             sb.Append(source == null
//                 ? " - source=null"
//                 : " - source="
//                   + getSourceURI().EncodeUri());
//             sb.Append(targetUri == null
//                 ? " - target=null"
//                 : " - target="
//                   + getTargetURI().EncodeUri());
//             sb.Append(targetMode == null
//                 ? ",targetMode=null"
//                 : ",targetMode="
//                   + targetMode.ToString());
//             return sb.ToString();
//         }
//     }
// }
//
// public enum TargetMode {
//     /** The relationship references a resource that is external to the package. */
//     INTERNAL,
//
//     /** The relationship references a part that is inside the package. */
//     EXTERNAL
// }