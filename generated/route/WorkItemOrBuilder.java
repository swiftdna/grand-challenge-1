// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route.proto

package route;

public interface WorkItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:route.WorkItem)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 id = 1;</code>
   * @return The id.
   */
  long getId();

  /**
   * <code>int64 origin = 2;</code>
   * @return The origin.
   */
  long getOrigin();

  /**
   * <code>int64 destination = 3;</code>
   * @return The destination.
   */
  long getDestination();

  /**
   * <code>string message = 4;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 4;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>int64 vowels = 5;</code>
   * @return The vowels.
   */
  long getVowels();
}
