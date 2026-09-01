package course.courseservice.infrastructure.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.UUID;

@MappedTypes(UUID.class)
public class UuidTypeHandler extends BaseTypeHandler<UUID> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter.toString(), Types.OTHER); // Use .toString() for compatibility
	}

	@Override
	public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return toUUID(rs.getString(columnName));
	}

	@Override
	public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return toUUID(rs.getString(columnIndex));
	}

	@Override
	public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return toUUID(cs.getString(columnIndex));
	}

	private UUID toUUID(String val) {
		if (val == null || val.isEmpty()) {
			return null;
		}
		return UUID.fromString(val.trim());
	}
}

