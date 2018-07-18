create PROCEDURE if_log_no_exist_and_insert(
  IN device_int_id BIGINT,
  IN device_id VARCHAR(32),
  IN log_time DATETIME,
  IN data LONGTEXT
)
  BEGIN
    declare is_exist int(4);
    DECLARE tmpDevice_int_id BIGINT;
    DECLARE tmpDevice_id VARCHAR(32);
    DECLARE tmpLog_time DATETIME;
    DECLARE tmpData LONGTEXT;

    set tmpDevice_id = device_id;
    set tmpDevice_int_id = device_int_id;
    set tmpLog_time = log_time;
    set tmpData = data;

    select count(*) from t_device_log t where t.log_time = log_time and t.device_id = device_id into is_exist;
    if is_exist <= 0
      then
      INSERT t_device_log(stateflag, time_stamp, create_time, device_int_id, device_id, data, log_time)
        VALUE (0,now(),now(),tmpDevice_int_id,tmpDevice_id,tmpData,tmpLog_time);
    END IF;
  END;