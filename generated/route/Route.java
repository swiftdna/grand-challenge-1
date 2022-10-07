// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route.proto

package route;

/**
 * Protobuf type {@code route.Route}
 */
public final class Route extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:route.Route)
    RouteOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Route.newBuilder() to construct.
  private Route(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Route() {
    path_ = "";
    payload_ = com.google.protobuf.ByteString.EMPTY;
    type_ = "";
    datapacket_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Route();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Route(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            id_ = input.readInt64();
            break;
          }
          case 16: {

            origin_ = input.readInt64();
            break;
          }
          case 24: {

            destination_ = input.readInt64();
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            path_ = s;
            break;
          }
          case 42: {

            payload_ = input.readBytes();
            break;
          }
          case 50: {
            java.lang.String s = input.readStringRequireUtf8();

            type_ = s;
            break;
          }
          case 58: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              datapacket_ = new java.util.ArrayList<route.WorkItem>();
              mutable_bitField0_ |= 0x00000001;
            }
            datapacket_.add(
                input.readMessage(route.WorkItem.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (com.google.protobuf.UninitializedMessageException e) {
      throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        datapacket_ = java.util.Collections.unmodifiableList(datapacket_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return route.RouteOuterClass.internal_static_route_Route_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return route.RouteOuterClass.internal_static_route_Route_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            route.Route.class, route.Route.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private long id_;
  /**
   * <code>int64 id = 1;</code>
   * @return The id.
   */
  @java.lang.Override
  public long getId() {
    return id_;
  }

  public static final int ORIGIN_FIELD_NUMBER = 2;
  private long origin_;
  /**
   * <code>int64 origin = 2;</code>
   * @return The origin.
   */
  @java.lang.Override
  public long getOrigin() {
    return origin_;
  }

  public static final int DESTINATION_FIELD_NUMBER = 3;
  private long destination_;
  /**
   * <code>int64 destination = 3;</code>
   * @return The destination.
   */
  @java.lang.Override
  public long getDestination() {
    return destination_;
  }

  public static final int PATH_FIELD_NUMBER = 4;
  private volatile java.lang.Object path_;
  /**
   * <code>string path = 4;</code>
   * @return The path.
   */
  @java.lang.Override
  public java.lang.String getPath() {
    java.lang.Object ref = path_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      path_ = s;
      return s;
    }
  }
  /**
   * <code>string path = 4;</code>
   * @return The bytes for path.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getPathBytes() {
    java.lang.Object ref = path_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      path_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PAYLOAD_FIELD_NUMBER = 5;
  private com.google.protobuf.ByteString payload_;
  /**
   * <code>bytes payload = 5;</code>
   * @return The payload.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getPayload() {
    return payload_;
  }

  public static final int TYPE_FIELD_NUMBER = 6;
  private volatile java.lang.Object type_;
  /**
   * <code>string type = 6;</code>
   * @return The type.
   */
  @java.lang.Override
  public java.lang.String getType() {
    java.lang.Object ref = type_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      type_ = s;
      return s;
    }
  }
  /**
   * <code>string type = 6;</code>
   * @return The bytes for type.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getTypeBytes() {
    java.lang.Object ref = type_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      type_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DATAPACKET_FIELD_NUMBER = 7;
  private java.util.List<route.WorkItem> datapacket_;
  /**
   * <code>repeated .route.WorkItem datapacket = 7;</code>
   */
  @java.lang.Override
  public java.util.List<route.WorkItem> getDatapacketList() {
    return datapacket_;
  }
  /**
   * <code>repeated .route.WorkItem datapacket = 7;</code>
   */
  @java.lang.Override
  public java.util.List<? extends route.WorkItemOrBuilder> 
      getDatapacketOrBuilderList() {
    return datapacket_;
  }
  /**
   * <code>repeated .route.WorkItem datapacket = 7;</code>
   */
  @java.lang.Override
  public int getDatapacketCount() {
    return datapacket_.size();
  }
  /**
   * <code>repeated .route.WorkItem datapacket = 7;</code>
   */
  @java.lang.Override
  public route.WorkItem getDatapacket(int index) {
    return datapacket_.get(index);
  }
  /**
   * <code>repeated .route.WorkItem datapacket = 7;</code>
   */
  @java.lang.Override
  public route.WorkItemOrBuilder getDatapacketOrBuilder(
      int index) {
    return datapacket_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (id_ != 0L) {
      output.writeInt64(1, id_);
    }
    if (origin_ != 0L) {
      output.writeInt64(2, origin_);
    }
    if (destination_ != 0L) {
      output.writeInt64(3, destination_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(path_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, path_);
    }
    if (!payload_.isEmpty()) {
      output.writeBytes(5, payload_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(type_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, type_);
    }
    for (int i = 0; i < datapacket_.size(); i++) {
      output.writeMessage(7, datapacket_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (id_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, id_);
    }
    if (origin_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, origin_);
    }
    if (destination_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, destination_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(path_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, path_);
    }
    if (!payload_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(5, payload_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(type_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, type_);
    }
    for (int i = 0; i < datapacket_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(7, datapacket_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof route.Route)) {
      return super.equals(obj);
    }
    route.Route other = (route.Route) obj;

    if (getId()
        != other.getId()) return false;
    if (getOrigin()
        != other.getOrigin()) return false;
    if (getDestination()
        != other.getDestination()) return false;
    if (!getPath()
        .equals(other.getPath())) return false;
    if (!getPayload()
        .equals(other.getPayload())) return false;
    if (!getType()
        .equals(other.getType())) return false;
    if (!getDatapacketList()
        .equals(other.getDatapacketList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getId());
    hash = (37 * hash) + ORIGIN_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getOrigin());
    hash = (37 * hash) + DESTINATION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getDestination());
    hash = (37 * hash) + PATH_FIELD_NUMBER;
    hash = (53 * hash) + getPath().hashCode();
    hash = (37 * hash) + PAYLOAD_FIELD_NUMBER;
    hash = (53 * hash) + getPayload().hashCode();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + getType().hashCode();
    if (getDatapacketCount() > 0) {
      hash = (37 * hash) + DATAPACKET_FIELD_NUMBER;
      hash = (53 * hash) + getDatapacketList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static route.Route parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static route.Route parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static route.Route parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static route.Route parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static route.Route parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static route.Route parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static route.Route parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static route.Route parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static route.Route parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static route.Route parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static route.Route parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static route.Route parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(route.Route prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code route.Route}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:route.Route)
      route.RouteOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return route.RouteOuterClass.internal_static_route_Route_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return route.RouteOuterClass.internal_static_route_Route_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              route.Route.class, route.Route.Builder.class);
    }

    // Construct using route.Route.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getDatapacketFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      id_ = 0L;

      origin_ = 0L;

      destination_ = 0L;

      path_ = "";

      payload_ = com.google.protobuf.ByteString.EMPTY;

      type_ = "";

      if (datapacketBuilder_ == null) {
        datapacket_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        datapacketBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return route.RouteOuterClass.internal_static_route_Route_descriptor;
    }

    @java.lang.Override
    public route.Route getDefaultInstanceForType() {
      return route.Route.getDefaultInstance();
    }

    @java.lang.Override
    public route.Route build() {
      route.Route result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public route.Route buildPartial() {
      route.Route result = new route.Route(this);
      int from_bitField0_ = bitField0_;
      result.id_ = id_;
      result.origin_ = origin_;
      result.destination_ = destination_;
      result.path_ = path_;
      result.payload_ = payload_;
      result.type_ = type_;
      if (datapacketBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          datapacket_ = java.util.Collections.unmodifiableList(datapacket_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.datapacket_ = datapacket_;
      } else {
        result.datapacket_ = datapacketBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof route.Route) {
        return mergeFrom((route.Route)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(route.Route other) {
      if (other == route.Route.getDefaultInstance()) return this;
      if (other.getId() != 0L) {
        setId(other.getId());
      }
      if (other.getOrigin() != 0L) {
        setOrigin(other.getOrigin());
      }
      if (other.getDestination() != 0L) {
        setDestination(other.getDestination());
      }
      if (!other.getPath().isEmpty()) {
        path_ = other.path_;
        onChanged();
      }
      if (other.getPayload() != com.google.protobuf.ByteString.EMPTY) {
        setPayload(other.getPayload());
      }
      if (!other.getType().isEmpty()) {
        type_ = other.type_;
        onChanged();
      }
      if (datapacketBuilder_ == null) {
        if (!other.datapacket_.isEmpty()) {
          if (datapacket_.isEmpty()) {
            datapacket_ = other.datapacket_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureDatapacketIsMutable();
            datapacket_.addAll(other.datapacket_);
          }
          onChanged();
        }
      } else {
        if (!other.datapacket_.isEmpty()) {
          if (datapacketBuilder_.isEmpty()) {
            datapacketBuilder_.dispose();
            datapacketBuilder_ = null;
            datapacket_ = other.datapacket_;
            bitField0_ = (bitField0_ & ~0x00000001);
            datapacketBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getDatapacketFieldBuilder() : null;
          } else {
            datapacketBuilder_.addAllMessages(other.datapacket_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      route.Route parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (route.Route) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private long id_ ;
    /**
     * <code>int64 id = 1;</code>
     * @return The id.
     */
    @java.lang.Override
    public long getId() {
      return id_;
    }
    /**
     * <code>int64 id = 1;</code>
     * @param value The id to set.
     * @return This builder for chaining.
     */
    public Builder setId(long value) {
      
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearId() {
      
      id_ = 0L;
      onChanged();
      return this;
    }

    private long origin_ ;
    /**
     * <code>int64 origin = 2;</code>
     * @return The origin.
     */
    @java.lang.Override
    public long getOrigin() {
      return origin_;
    }
    /**
     * <code>int64 origin = 2;</code>
     * @param value The origin to set.
     * @return This builder for chaining.
     */
    public Builder setOrigin(long value) {
      
      origin_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 origin = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearOrigin() {
      
      origin_ = 0L;
      onChanged();
      return this;
    }

    private long destination_ ;
    /**
     * <code>int64 destination = 3;</code>
     * @return The destination.
     */
    @java.lang.Override
    public long getDestination() {
      return destination_;
    }
    /**
     * <code>int64 destination = 3;</code>
     * @param value The destination to set.
     * @return This builder for chaining.
     */
    public Builder setDestination(long value) {
      
      destination_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 destination = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDestination() {
      
      destination_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object path_ = "";
    /**
     * <code>string path = 4;</code>
     * @return The path.
     */
    public java.lang.String getPath() {
      java.lang.Object ref = path_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        path_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string path = 4;</code>
     * @return The bytes for path.
     */
    public com.google.protobuf.ByteString
        getPathBytes() {
      java.lang.Object ref = path_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        path_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string path = 4;</code>
     * @param value The path to set.
     * @return This builder for chaining.
     */
    public Builder setPath(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      path_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string path = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearPath() {
      
      path_ = getDefaultInstance().getPath();
      onChanged();
      return this;
    }
    /**
     * <code>string path = 4;</code>
     * @param value The bytes for path to set.
     * @return This builder for chaining.
     */
    public Builder setPathBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      path_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes payload = 5;</code>
     * @return The payload.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }
    /**
     * <code>bytes payload = 5;</code>
     * @param value The payload to set.
     * @return This builder for chaining.
     */
    public Builder setPayload(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      payload_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes payload = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayload() {
      
      payload_ = getDefaultInstance().getPayload();
      onChanged();
      return this;
    }

    private java.lang.Object type_ = "";
    /**
     * <code>string type = 6;</code>
     * @return The type.
     */
    public java.lang.String getType() {
      java.lang.Object ref = type_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        type_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string type = 6;</code>
     * @return The bytes for type.
     */
    public com.google.protobuf.ByteString
        getTypeBytes() {
      java.lang.Object ref = type_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        type_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string type = 6;</code>
     * @param value The type to set.
     * @return This builder for chaining.
     */
    public Builder setType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string type = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearType() {
      
      type_ = getDefaultInstance().getType();
      onChanged();
      return this;
    }
    /**
     * <code>string type = 6;</code>
     * @param value The bytes for type to set.
     * @return This builder for chaining.
     */
    public Builder setTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      type_ = value;
      onChanged();
      return this;
    }

    private java.util.List<route.WorkItem> datapacket_ =
      java.util.Collections.emptyList();
    private void ensureDatapacketIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        datapacket_ = new java.util.ArrayList<route.WorkItem>(datapacket_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        route.WorkItem, route.WorkItem.Builder, route.WorkItemOrBuilder> datapacketBuilder_;

    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public java.util.List<route.WorkItem> getDatapacketList() {
      if (datapacketBuilder_ == null) {
        return java.util.Collections.unmodifiableList(datapacket_);
      } else {
        return datapacketBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public int getDatapacketCount() {
      if (datapacketBuilder_ == null) {
        return datapacket_.size();
      } else {
        return datapacketBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public route.WorkItem getDatapacket(int index) {
      if (datapacketBuilder_ == null) {
        return datapacket_.get(index);
      } else {
        return datapacketBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder setDatapacket(
        int index, route.WorkItem value) {
      if (datapacketBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDatapacketIsMutable();
        datapacket_.set(index, value);
        onChanged();
      } else {
        datapacketBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder setDatapacket(
        int index, route.WorkItem.Builder builderForValue) {
      if (datapacketBuilder_ == null) {
        ensureDatapacketIsMutable();
        datapacket_.set(index, builderForValue.build());
        onChanged();
      } else {
        datapacketBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder addDatapacket(route.WorkItem value) {
      if (datapacketBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDatapacketIsMutable();
        datapacket_.add(value);
        onChanged();
      } else {
        datapacketBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder addDatapacket(
        int index, route.WorkItem value) {
      if (datapacketBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDatapacketIsMutable();
        datapacket_.add(index, value);
        onChanged();
      } else {
        datapacketBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder addDatapacket(
        route.WorkItem.Builder builderForValue) {
      if (datapacketBuilder_ == null) {
        ensureDatapacketIsMutable();
        datapacket_.add(builderForValue.build());
        onChanged();
      } else {
        datapacketBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder addDatapacket(
        int index, route.WorkItem.Builder builderForValue) {
      if (datapacketBuilder_ == null) {
        ensureDatapacketIsMutable();
        datapacket_.add(index, builderForValue.build());
        onChanged();
      } else {
        datapacketBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder addAllDatapacket(
        java.lang.Iterable<? extends route.WorkItem> values) {
      if (datapacketBuilder_ == null) {
        ensureDatapacketIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, datapacket_);
        onChanged();
      } else {
        datapacketBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder clearDatapacket() {
      if (datapacketBuilder_ == null) {
        datapacket_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        datapacketBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public Builder removeDatapacket(int index) {
      if (datapacketBuilder_ == null) {
        ensureDatapacketIsMutable();
        datapacket_.remove(index);
        onChanged();
      } else {
        datapacketBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public route.WorkItem.Builder getDatapacketBuilder(
        int index) {
      return getDatapacketFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public route.WorkItemOrBuilder getDatapacketOrBuilder(
        int index) {
      if (datapacketBuilder_ == null) {
        return datapacket_.get(index);  } else {
        return datapacketBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public java.util.List<? extends route.WorkItemOrBuilder> 
         getDatapacketOrBuilderList() {
      if (datapacketBuilder_ != null) {
        return datapacketBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(datapacket_);
      }
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public route.WorkItem.Builder addDatapacketBuilder() {
      return getDatapacketFieldBuilder().addBuilder(
          route.WorkItem.getDefaultInstance());
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public route.WorkItem.Builder addDatapacketBuilder(
        int index) {
      return getDatapacketFieldBuilder().addBuilder(
          index, route.WorkItem.getDefaultInstance());
    }
    /**
     * <code>repeated .route.WorkItem datapacket = 7;</code>
     */
    public java.util.List<route.WorkItem.Builder> 
         getDatapacketBuilderList() {
      return getDatapacketFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        route.WorkItem, route.WorkItem.Builder, route.WorkItemOrBuilder> 
        getDatapacketFieldBuilder() {
      if (datapacketBuilder_ == null) {
        datapacketBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            route.WorkItem, route.WorkItem.Builder, route.WorkItemOrBuilder>(
                datapacket_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        datapacket_ = null;
      }
      return datapacketBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:route.Route)
  }

  // @@protoc_insertion_point(class_scope:route.Route)
  private static final route.Route DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new route.Route();
  }

  public static route.Route getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Route>
      PARSER = new com.google.protobuf.AbstractParser<Route>() {
    @java.lang.Override
    public Route parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Route(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Route> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Route> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public route.Route getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

