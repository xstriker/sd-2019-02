defmodule ServerWeb.CurriculumControllerTest do
  use ServerWeb.ConnCase

  alias Server.JobServer
  alias Server.JobServer.Curriculum

  @create_attrs %{
    area: "some area",
    contato: "some contato",
    nome: "some nome",
    salario: 42,
    tempo: 42
  }
  @update_attrs %{
    area: "some updated area",
    contato: "some updated contato",
    nome: "some updated nome",
    salario: 43,
    tempo: 43
  }
  @invalid_attrs %{area: nil, contato: nil, nome: nil, salario: nil, tempo: nil}

  def fixture(:curriculum) do
    {:ok, curriculum} = JobServer.create_curriculum(@create_attrs)
    curriculum
  end

  setup %{conn: conn} do
    {:ok, conn: put_req_header(conn, "accept", "application/json")}
  end

  describe "index" do
    test "lists all curriculuns", %{conn: conn} do
      conn = get(conn, Routes.curriculum_path(conn, :index))
      assert json_response(conn, 200)["data"] == []
    end
  end

  describe "create curriculum" do
    test "renders curriculum when data is valid", %{conn: conn} do
      conn = post(conn, Routes.curriculum_path(conn, :create), curriculum: @create_attrs)
      assert %{"id" => id} = json_response(conn, 201)["data"]

      conn = get(conn, Routes.curriculum_path(conn, :show, id))

      assert %{
               "id" => id,
               "area" => "some area",
               "contato" => "some contato",
               "nome" => "some nome",
               "salario" => 42,
               "tempo" => 42
             } = json_response(conn, 200)["data"]
    end

    test "renders errors when data is invalid", %{conn: conn} do
      conn = post(conn, Routes.curriculum_path(conn, :create), curriculum: @invalid_attrs)
      assert json_response(conn, 422)["errors"] != %{}
    end
  end

  describe "update curriculum" do
    setup [:create_curriculum]

    test "renders curriculum when data is valid", %{conn: conn, curriculum: %Curriculum{id: id} = curriculum} do
      conn = put(conn, Routes.curriculum_path(conn, :update, curriculum), curriculum: @update_attrs)
      assert %{"id" => ^id} = json_response(conn, 200)["data"]

      conn = get(conn, Routes.curriculum_path(conn, :show, id))

      assert %{
               "id" => id,
               "area" => "some updated area",
               "contato" => "some updated contato",
               "nome" => "some updated nome",
               "salario" => 43,
               "tempo" => 43
             } = json_response(conn, 200)["data"]
    end

    test "renders errors when data is invalid", %{conn: conn, curriculum: curriculum} do
      conn = put(conn, Routes.curriculum_path(conn, :update, curriculum), curriculum: @invalid_attrs)
      assert json_response(conn, 422)["errors"] != %{}
    end
  end

  describe "delete curriculum" do
    setup [:create_curriculum]

    test "deletes chosen curriculum", %{conn: conn, curriculum: curriculum} do
      conn = delete(conn, Routes.curriculum_path(conn, :delete, curriculum))
      assert response(conn, 204)

      assert_error_sent 404, fn ->
        get(conn, Routes.curriculum_path(conn, :show, curriculum))
      end
    end
  end

  defp create_curriculum(_) do
    curriculum = fixture(:curriculum)
    {:ok, curriculum: curriculum}
  end
end
