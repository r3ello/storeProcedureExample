CREATE OR REPLACE FUNCTION public.sum_interest_rate (
  double precision
)
RETURNS integer AS
$body$
DECLARE
    affected_rows integer;

BEGIN
 UPDATE account set balance = balance+(balance * $1);
 GET DIAGNOSTICS affected_rows = ROW_COUNT;
 RETURN affected_rows;
END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;

ALTER FUNCTION public.sum_interest_rate (double precision)
  OWNER TO postgres;