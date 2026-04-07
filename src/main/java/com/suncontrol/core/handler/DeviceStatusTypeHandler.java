package com.suncontrol.core.handler;

import com.suncontrol.core.constant.asset.DeviceStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedTypes(DeviceStatus.class)
public class DeviceStatusTypeHandler extends BaseTypeHandler<DeviceStatus> {

    @Override
    public void setNonNullParameter(
            PreparedStatement ps, int i, DeviceStatus parameter, JdbcType jdbcType)
            throws SQLException {
        // 객체 -> DB (Enum의 코드를 숫자로 저장)
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public DeviceStatus getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        // DB -> 객체 (컬럼명으로 찾기)
        int code = rs.getInt(columnName);
        return DeviceStatus.fromCode(code);
    }

    @Override
    public DeviceStatus getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // DB -> 객체 (인덱스로 찾기)
        int code = rs.getInt(columnIndex);
        return DeviceStatus.fromCode(code);
    }

    @Override
    public DeviceStatus getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        // 프로시저 호출 시 사용
        int code = cs.getInt(columnIndex);
        return DeviceStatus.fromCode(code);
    }
}