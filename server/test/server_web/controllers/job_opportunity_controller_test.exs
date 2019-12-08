defmodule ServerWeb.JobOpportunityControllerTest do
  use ServerWeb.ConnCase

  alias Server.JobServer
  alias Server.JobServer.JobOpportunity

  @create_attrs %{
    area: "some area",
    contato: "some contato",
    nomeEmpresa: "some nomeEmpresa",
    salario: 42,
    tempo: 42
  }
  @update_attrs %{
    area: "some updated area",
    contato: "some updated contato",
    nomeEmpresa: "some updated nomeEmpresa",
    salario: 43,
    tempo: 43
  }
  @invalid_attrs %{area: nil, contato: nil, nomeEmpresa: nil, salario: nil, tempo: nil}

  def fixture(:job_opportunity) do
    {:ok, job_opportunity} = JobServer.create_job_opportunity(@create_attrs)
    job_opportunity
  end

  setup %{conn: conn} do
    {:ok, conn: put_req_header(conn, "accept", "application/json")}
  end

  describe "index" do
    test "lists all opportunities", %{conn: conn} do
      conn = get(conn, Routes.job_opportunity_path(conn, :index))
      assert json_response(conn, 200)["data"] == []
    end
  end

  describe "create job_opportunity" do
    test "renders job_opportunity when data is valid", %{conn: conn} do
      conn = post(conn, Routes.job_opportunity_path(conn, :create), job_opportunity: @create_attrs)
      assert %{"id" => id} = json_response(conn, 201)["data"]

      conn = get(conn, Routes.job_opportunity_path(conn, :show, id))

      assert %{
               "id" => id,
               "area" => "some area",
               "contato" => "some contato",
               "nomeEmpresa" => "some nomeEmpresa",
               "salario" => 42,
               "tempo" => 42
             } = json_response(conn, 200)["data"]
    end

    test "renders errors when data is invalid", %{conn: conn} do
      conn = post(conn, Routes.job_opportunity_path(conn, :create), job_opportunity: @invalid_attrs)
      assert json_response(conn, 422)["errors"] != %{}
    end
  end

  describe "update job_opportunity" do
    setup [:create_job_opportunity]

    test "renders job_opportunity when data is valid", %{conn: conn, job_opportunity: %JobOpportunity{id: id} = job_opportunity} do
      conn = put(conn, Routes.job_opportunity_path(conn, :update, job_opportunity), job_opportunity: @update_attrs)
      assert %{"id" => ^id} = json_response(conn, 200)["data"]

      conn = get(conn, Routes.job_opportunity_path(conn, :show, id))

      assert %{
               "id" => id,
               "area" => "some updated area",
               "contato" => "some updated contato",
               "nomeEmpresa" => "some updated nomeEmpresa",
               "salario" => 43,
               "tempo" => 43
             } = json_response(conn, 200)["data"]
    end

    test "renders errors when data is invalid", %{conn: conn, job_opportunity: job_opportunity} do
      conn = put(conn, Routes.job_opportunity_path(conn, :update, job_opportunity), job_opportunity: @invalid_attrs)
      assert json_response(conn, 422)["errors"] != %{}
    end
  end

  describe "delete job_opportunity" do
    setup [:create_job_opportunity]

    test "deletes chosen job_opportunity", %{conn: conn, job_opportunity: job_opportunity} do
      conn = delete(conn, Routes.job_opportunity_path(conn, :delete, job_opportunity))
      assert response(conn, 204)

      assert_error_sent 404, fn ->
        get(conn, Routes.job_opportunity_path(conn, :show, job_opportunity))
      end
    end
  end

  defp create_job_opportunity(_) do
    job_opportunity = fixture(:job_opportunity)
    {:ok, job_opportunity: job_opportunity}
  end
end
